<h1> Aplikasi Belajar Anak </h1>

<img src="/static/ABACurrentLogo.png">

<h2> What is ABA </h2>

<p>ABA is an application to help toddlers learn to read and write by using multisensory materials. ABA is designed to stimulate active learning that focuses on toddler the toddler learning development stage. We use the Montessori learning methods that provides and environment to increases their curiosity, independence, and exploration that is added with hands-on practices. The method develops two learning domains: cognitive -- which the knowledge of absorbing information and how that develops by appliances; and psychomotor -- which is physicality and how that develops by appliances. In tracking the toddlers' competency development, we rely on Bloom's Taxonomy.</p>

<h2>How to use ABA?</h2>

<p>ABA is currently an android application that uses Google Cloud Platform as the cloud service. There are currently 4 vital features in ABA, namely
    <ol>
        <li><strong>Learning Section</strong>
            <ul>
                <li>Eksplor Huruf dan Eksplor Angka</li>
                <li>KBBI Anak</li>
            </ul>
        </li>
        <li><strong>Practice Section</strong>
            <ul>
                <li>Latihan Menyusun Huruf</li>
                <li>Latihan Mengeja Huruf</li>
                <li>Latihan Mengeja Kata (Not shown in the presentation)</li>
            </ul>
        </li>
    </ol>

<h3>Learning Section</h3>
<h4>Eksplor Huruf and Eksplor Angka</h4>
<p>To use this feature, toddler can click the alphabets on the screen and the app will output a pronunciation of that particular alphabet. This will not only teach toddler about the alphabet by sight, but also by hearing such that children will memorize how the alphabet is pronounce.</p>

<h2>Machine Learning Documentation</h2>
<h3>KBBI Anak</h2>
<p>KBBI Anak is taken from 109 children's e-books from the following <a href="https://badanbahasa.kemdikbud.go.id/produk-detail/751/bahan-bacaan-literasi/">link</a>. Each e-book is extracted and merged into one file using <a href="https://pymupdf.readthedocs.io/">PyMuPDF</a>. Then, we stemming each word into a basic word using <a href = "https://github.com/sastrawi/sastrawi">Sastrawi</a>. Next, we matched each word with the KBBI dataset by <a href = "https://datahub.io/aps2201/kateglo_scrape">Kateglo</a>. We only use words found in the KBBI. We also added the meaning of each word in the KBBI. We only use verbs and nouns in KBBI. All of this we do so that toddlers get words that are easy for them to understand. Next we also add an illustration to each word. KBBI Anak have more than 3000 words that are ready to be learned.</p>
<p>Word Recommendation System At KBBI Anak, we created a simple algorithm for word recommendations to be displayed. The words that will be displayed are based on <a href="https://www.rimakata.com/">Rimakata</a>. "Rima" used in this recommendation system is a "rima akhir sempurna". If there are no matching words in the dataset, the system will randomize the words to be displayed. In making this recommendation system, we also use the following <a href="https://github.com/Kylamber/pemenggalan-kata-indonesia">code</a>. In our application we divide the word dataset into "Mudah", "Sedang" and "Sulit" based on the number of letters.</p>

<h3>Latihan Mengeja Huruf</h3>
<p>Latihan Mengeja Huruf is ABA's feature that involves speech recognition. The required dataset for this feature is an audio dataset. This dataset contains the pronounciation of 26 alphabets from A to Z which was collected by our team members. There are 2600 audio files (in wav extension) that used in the training process.</p>
<p>First thing to do is labeling each dataset from 0 to 25 which represent each alphabet respectively. After that we need to extract a feature from audio file called Mel-Frequency Ceptral Coefficients (MFCCs) and then begin to build the model. The model used is a deep learning model containing 2 convolution layers and 1 connected layer. The resulting model is astonishing due to the fact that the average accuracy sits approximately at 98%.</p>

<h3>Latihan Mengeja Kata</h3>
<p>Latihan Mengeja Kata is ABA's feature that involves speech recognition. The required dataset for this feature is an audio dataset. This dataset contains the pronounciation of 5 words which are "Adik", "Ayah", "Ibu", "Kakak", and "Keluarga" which was collected by our team members. There are 200 audio files (in wav extension) that used in the training process.</p>
<p>First thing to do is labeling each dataset from 0 to 4 which represent each word respectively. After that we need to extract a feature from audio file called Mel-Frequency Ceptral Coefficients (MFCCs) and then begin to build the model. The model used is a deep learning model containing 2 convolution layers and 1 connected layer. The resulting model is pretty good due to the fact that the average accuracy sits approximately at 90%.</p>
