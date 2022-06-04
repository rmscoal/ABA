import pandas as pd
import json
import os
import numpy as np
import sys
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

DIR_JSON =  os.path.join(os.path.dirname(__file__), 'words', 'listWords.json')
DIR_MUDAH = os.path.join(os.path.dirname(__file__), 'words', 'listMudah.json')
DIR_SEDANG = os.path.join(os.path.dirname(__file__), 'words', 'listSedang.json')
DIR_SULIT = os.path.join(os.path.dirname(__file__), 'words', 'listSulit.json')

# import json and change to dataframe
data_json = pd.read_json(DIR_JSON)
data_mudah = pd.read_json(DIR_MUDAH)
data_sedang = pd.read_json(DIR_SEDANG)
data_sulit = pd.read_json(DIR_SULIT)

vocals = list('aiueo*()-')
diftong = list('*()-')
gabungan = {'kh':'!',
			'ng':'@',
			'sy':'#',
			'ny':'$',
			'tr':'%',
			'gr':'^',
			'ai':'*',
			'ei':'(',
			'au':')',
			'oi':'-',}

def replacer(word):
	'''
	Replace the input string based on the gabungan variable
	'''
	result = word
	for letters, symbol in gabungan.items():
		result = result.replace(letters, symbol)
	return result

def unreplace(syllables):
	'''
	Unreplace the input string based on the gabungan variable
	'''
	result = []
	for syllable in syllables:
		sub_result = syllable
		for letters, symbol in gabungan.items():
			sub_result = sub_result.replace(symbol, letters)
		result.append(sub_result)
	return result

def preprocess(word):
	'''
	Split the word based on the rule:
	A consonant always has a vocal as its friend.
	'''
	result = []
	tempchar = ""
	last_consonant = False
	for letter in word:
		is_consonant = (letter not in vocals)
		if is_consonant:
			last_consonant = True
			tempchar += letter
		else:
			if last_consonant:
				# To avoid letters like 'nda' by changing it to 'n, da'
				if len(tempchar) > 1:
					result.append(tempchar[0])
					result.append(tempchar[1:] + letter)
				else:
					result.append(tempchar + letter)
				tempchar = ""
			else:
				result.append(letter)
			last_consonant = False
	# If there is an excess tempchar, dump it.
	if len(tempchar) > 0:
		result.append(tempchar)
	#print(result)
	return result

def process(syllables):
	'''
	Finalize the preprocessed syllables
	'''
	result = []
	while True:
		try:
			if not contains(vocals, syllables[1]): # Checking if the next letter is a non vocal such as [ple, 'ks']
				if len(syllables[1]) == 1:
					if contains(diftong, syllables[0]): # Checking for words such as [s'ai', n] and turn it into [sa, in]
						new_word = join(unreplace(syllables[0]))
						result.append(join(new_word[:len(new_word)-1]))
						syllables.insert(0, new_word[-1])
						del syllables[1]
					else: # e.g [si, n] becomes [sin]
						result.append(join(syllables[:2]))
						del syllables[:2]
				else:
					result.append(join(syllables[:2]))
					del syllables[:2]
			elif not contains(vocals, syllables[0]):
				if not contains(vocals, syllables[2]): # Avoids [s, pe, 'k', tru, m]
					result.append(join(syllables[:3]))
					del syllables[:3]
				else:
					result.append(join(syllables[:2]))
					del syllables[:2]
			else:
				result.append(syllables[0])
				del syllables[0]
		except IndexError:
			if syllables:
				result.append(syllables[0])
				del syllables[0]
			break

	if '%an' in result:		# Special check for trans e.g tran.smi.gra.si -> trans.mi.gra.si
		indx = result.index('%an')
		try:
			if result[indx + 1][0] == 's':
				if result[indx + 1][1] not in vocals:
					result[indx] = '%ans'
					result[indx + 1] = result[indx + 1].replace('s', '')
		except IndexError:
			pass
	elif 'ek' in result:		# Special check for trans e.g ek.skre.si -> eks.kre.si
		indx = result.index('ek')
		try:
			if result[indx + 1][0] == 's':
				if result[indx + 1][1] not in vocals:
					result[indx] = 'eks'
					result[indx + 1] = result[indx + 1].replace('s', '')
		except IndexError:
			pass
	return result

def join(letters):
	'''
	Turns a list into one single string
	'''
	return ''.join(letters)

def contains(items, letters):
	'''
	Check if a string contains character from specified item
	'''
	for item in items:
		if letters.__contains__(item):
			return True
	return False


def syllabify(word):
	replaced_word = replacer(word)
	syllables = preprocess(replaced_word)
	processed_syllables = process(syllables)
	return unreplace(processed_syllables)

def cari(word,kamus):
    for i in range(len(kamus)):
        if (syllabify(kamus.iat[i,0])[-1]==syllabify(word)[-1])&(kamus.iat[i,0]!=word):
            return kamus.iloc[i],i
        
    return None
    
kamus1 = data_json.copy()
kamus_mudah = data_mudah.copy()
kamus_sedang = data_sedang.copy()
kamus_sulit = data_sulit.copy()

def rimakata(word,kamus):
    recom=cari(word,kamus)
    if recom is None:
        recom=kamus.sample()
        kamus.drop(recom.index.values.astype(int)[0],inplace=True)
        kamus.reset_index(drop=True,inplace=True)
        return recom.squeeze()
        #print(recom['lema'])
        #print(recom['nilai'])
    else:
        recom=recom[0]
        kamus.drop(index=cari(word,kamus)[1],inplace=True)
        kamus.reset_index(drop=True,inplace=True)
        return recom
        #print(recom['lema'])
        #print(recom['nilai'])

def randKata(kamus1 = kamus1, kamus2 = kamus_mudah, kamus3 = kamus_sedang, kamus4 = kamus_sulit):
    hasil_mudah = []
    hasil_sedang = []
    hasil_susah = []
    dic = {'lema': '', 'nilai':'', 'url':''}
    cari2 = rimakata(data_json['lema'][np.random.randint(0, len(data_json))], kamus1).to_list()[0]
    print(cari2)
    for i in range(4):
        for j in range(3):
            #temp = pd.Series.to_list(rimakata('maka', kamus1))
            # mudah
            #print(j)
            if j == 0:
                hasil_mudahh = pd.Series.to_list(rimakata(cari2, kamus_mudah))
                dic['lema'] = hasil_mudahh[0]
                dic['nilai'] = hasil_mudahh[1]
                dic['url'] = hasil_mudahh[2]
                hasil_mudah.append(dic.copy())
                #print("mudah: ", hasil_mudah)
            elif j == 1:
                hasil_sedangg = pd.Series.to_list(rimakata(cari2, kamus_sedang))
                dic['lema'] = hasil_sedangg[0]
                dic['nilai'] = hasil_sedangg[1]
                dic['url'] = hasil_sedangg[2]
                hasil_sedang.append(dic.copy())
                #print("sedang: ", hasil_sedang)
            elif j == 2:
                hasil_sulitt = pd.Series.to_list(rimakata(cari2, kamus_sulit))
                dic['lema'] = hasil_sulitt[0]
                dic['nilai'] = hasil_sulitt[1]
                dic['url'] = hasil_sulitt[2]
                hasil_susah.append(dic.copy())
    return [hasil_mudah, hasil_sedang, hasil_susah]

def main():
    res = randKata()
    # Serializing json 
    json_object = json.dumps(res[2], indent = 4)
    json_object2 = json.dumps(res[0], indent = 4)
    json_object3 = json.dumps(res[1], indent = 4)

    # Writing to sample.json
    with open(os.path.join(os.path.dirname(__file__), 'rimakataResult', 'resultSulit.json'), "w") as outfile:
        outfile.write(json_object)
    with open(os.path.join(os.path.dirname(__file__), 'rimakataResult', 'resultMudah.json'), "w") as outfile2:
        outfile2.write(json_object2)
    with open(os.path.join(os.path.dirname(__file__), 'rimakataResult', 'resultSedang.json'), "w") as outfile3:
        outfile3.write(json_object3)
        
    return 1

if __name__ == "__main__":
    try:
        print(main())
    except Exception as err:
        print(Exception, err)
