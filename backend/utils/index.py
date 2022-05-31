import librosa
import numpy as np
import json
from json import JSONEncoder  
import sys
import os

class NumpyArrayEncoder(JSONEncoder):
    def default(self, obj):
        if isinstance(obj, np.ndarray):
            return obj.tolist()
        return JSONEncoder.default(self, obj)

def main(path):
    signal, fs = librosa.load(path, sr=16000)
    signal = librosa.effects.trim(signal)[0]
    signal_new = librosa.util.fix_length(signal, size=16000)
    mfccs = librosa.feature.mfcc(y=signal_new,sr=fs, n_mfcc=13, n_fft=512).T
    mfccs=mfccs.reshape(mfccs.shape[0], mfccs.shape[1],1)
    numpyData = {"array": np.ravel(mfccs)}
    encodedNumpyData = json.dumps(numpyData, cls=NumpyArrayEncoder)
    with open(os.path.join(os.path.dirname(__file__), 'mfccsResult', 'result.json'), 'w') as file:
        json.dump(encodedNumpyData, file)

    return 1 


if __name__ == '__main__':
        try: 
            print(main(sys.argv[1]))
        except Exception as err:
            print(Exception, err)
            