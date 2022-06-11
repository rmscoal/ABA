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
                <li>Latihan Mengeja Kata (Demo is shown in the presentation)</li>
            </ul>
        </li>
    </ol>

<h3>Learning Section</h3>
<h4>Eksplor Huruf and Eksplor Angka</h4>
<p>To use this feature, toddler can click the alphabets on the screen and the app will output a pronunciation of that particular alphabet. This will not only teach toddler about the alphabet by sight, but also by hearing such that children will memorize how the alphabet is pronounce.</p>
<h4>KBBI Anak</h4>
<p> KBBI Anak is a feature for toddler to explore Indonesian words that we have designed to be child-friendly. In this feature, toddlers can know the word, know the meaning of the word, and imagine what the word is like in real-life since we included illustrations with the word.</p>
<h3>Practice Section</h3>
<h4>Latihan Menyusun Huruf</h4>
<p>This feature is a designed as a practice for toddlers after what they have learnt in eksplor huruf. Here toddlers can implement the knowledge of letters.</p>
<h4>Latihan Mengeja Huruf</h4>
<p>This feature is designed for toddlers to practice pronouncing the alphabets. Here, toddlers can implement the knowledge of how should the alphabets be pronounced in Indonesian. Hence, this touches cognitive and psychomotor abilities.</p>

<h2>Machine Learning Documentation</h2>
<h3>KBBI Anak</h2>
<ol>
    <li><strong>KBBI Anak</strong>
        <ul>KBBI Anak is taken from 109 children's e-books from the following <a href="https://badanbahasa.kemdikbud.go.id/produk-detail/751/bahan-bacaan-literasi/">link</a>. Each e-book is extracted and merged into one file using <a href="https://pymupdf.readthedocs.io/">PyMuPDF</a>. Then, we stemming each word into a basic word using <a href = "https://github.com/sastrawi/sastrawi">Sastrawi</a>. Next, we matched each word with the KBBI dataset by <a href = "https://datahub.io/aps2201/kateglo_scrape">Kateglo</a>. We only use words found in the KBBI. We also added the meaning of each word in the KBBI. We only use verbs and nouns in KBBI. All of this we do so that toddlers get words that are easy for them to understand. Next, we also add an illustration to each word. KBBI Anak have more than 3000 words that are ready to be learned.</ul>
    </li>
    <li><strong>Word Recommendation System</strong>
        <ul>At KBBI Anak, we created a simple algorithm for word recommendations to be displayed. The words that will be displayed are based on <a href="https://www.rimakata.com/">Rimakata</a>. "Rima" used in this recommendation system is a "rima akhir sempurna". If there are no matching words in the dataset, the system will randomize the words to be displayed. In making this recommendation system, we also use the following <a href="https://github.com/Kylamber/pemenggalan-kata-indonesia">code</a>. In our application we divide the word dataset into "Mudah", "Sedang" and "Sulit" based on the number of letters.</ul>
    </li>
</ol>

<h3>Latihan Mengeja Huruf</h3>
<p><strong>Latihan Mengeja Huruf</strong> is ABA's feature that involves speech recognition. The required dataset for this feature is an audio dataset. This dataset contains the pronounciation of 26 alphabets from A to Z which was collected by our team members. There are 2600 audio files (in wav extension) that used in the training process.</p>
<p>First thing to do is labeling each dataset from 0 to 25 which represent each alphabet respectively. After that we need to extract a feature from audio file called Mel-Frequency Ceptral Coefficients (MFCCs) and then begin to build the model. The model used is a deep learning model containing 2 convolution layers and 1 connected layer. The resulting model is astonishing due to the fact that the average accuracy sits approximately at 98%.</p>

<h3>Latihan Mengeja Kata</h3>
<p><strong>Latihan Mengeja Kata</strong> is ABA's feature that involves speech recognition. The required dataset for this feature is an audio dataset. This dataset contains the pronounciation of 5 words which are "Adik", "Ayah", "Ibu", "Kakak", and "Keluarga" which was collected by our team members. There are 200 audio files (in wav extension) that used in the training process.</p>
<p>First thing to do is labeling each dataset from 0 to 4 which represent each word respectively. After that we need to extract a feature from audio file called Mel-Frequency Ceptral Coefficients (MFCCs) and then begin to build the model. The model used is a deep learning model containing 2 convolution layers and 1 connected layer. The resulting model is pretty good due to the fact that the average accuracy sits approximately at 90%.</p>

<h2>Cloud Computing Documentation</h2>
<p>The cloud computing team is responsible on designing the API for the frontend, a.k.a android application. All of the CC codes are available in the <strong>backend folder</strong>. We use Node.js and Express.js as the framework. The main code of this can bee seen in <strong>app.js</strong> file. The routes and handlers can be seen in <strong><em>backend/routes</em></strong> and <strong><em>backend/handlers</em></strong> folders respectively.
<p>The cloud computing team is also responsible for the machine learning deployments. With the help of <code>tensorflowjs</code>, we were able to do this. In <strong>Latihan Mengeja Huruf</strong> feature, the android application sends the recording in a .m4a format and we were able to catch the file sent using <strong>multer</strong>. Then, using <strong>ffmpeg</strong> we were able to convert this file into .wav format. Then, we run a python script -- can be seen in <strong><em>backend/utils/index.py</em></strong> to collect the MFCC matric from the audio file. Finally, we flatten this matrix into an array then make it into a Tensor and insert it to the model that is available in <strong><em>backend/models</em></strong>.</p>
<p>The cloud computing team also design the infrastructure of the cloud using Google Cloud Platform. The initial plan, was to use <strong>Regional TCP Load Balancers</strong>. Hence, we updated our code and make a new route that is <code>'/'</code> that acts the health checks done by the load balancer. This route is to ensure that the app is healthy and able to send request to the backend services.</p>
<p>Currently, below is the diagram of the design of our GCP's services.</p>
<img src="/static/gcp2.png" height="400px" width="auto"/>
<p>Finally, on time efficiency when making a new compute instance, we made a shell script such that it automatically runs when a new VM is created for <strong>Managed Instance Group</strong>. The shell script can be seen in <strong><em>backend/startup.sh</em></strong>. Lastly, we use <strong>pm2</strong> -- a daemon process manager that will help you manage and keep your application online -- to ensure that our app is running! To do this we install pm2 globally with npm (available in the startup script) and then run app.js with pm2 by doing <code>pm2 start app.js</code>.</p>
<h6>Now let's take a deeper look at our backend code</h6>
We currently have 5 routes, where 4 of them requires firebase authentication token since these will be used in the app. Meanwhile, the <code>'/'</code> as mentioned is for the health checks.
```js
/* 
@ ROUTES
*/
app.use('/users', middlewareFirebase.decodeToken, userRoute);
app.use('/achievements', middlewareFirebase.decodeToken, achievementRoute);
app.use('/predictions', middlewareFirebase.decodeToken, predictionRoute);
app.use('/rimakatawords', middlewareFirebase.decodeToken, rimakataRoute);
app.use('/', healthRoute);
```
<p> The code for middlewareFirebase is located in <strong><em>backend/middleware</em></strong> folder. We designed this to be a class. The route that has <code>GET</code> method is <code>'/users'</code> and <code>'/rimakatawords'</code>. Meanwhile, the route <code>'/achievements'</code> has <code>PUT</code> method. Lastly, the <code>'/predictions'</code> has <code>POST</code> method.</p>
<p>Without further due, let's see the successes result with these routes.</p>
<h5><strong>Users HTTP Response</strong></h5>
```json
{
    "status": "OK",
    "message": "Successfully extract data with OCR.",
    "data": {
        "uid": "q6fvJpSVtIhS2XGqqH8L",
        "province": "JAWA TIMUR",
        "district": "KABUPATEN SIDOARJO",
        "id_number": "351XXXXXXXXXXXXX",
        "name": "MOCHAMMAD ARYA SALSABILA",
        "place_date_of_birth": "SIDOARJO, 24-06-2001",
        "gender": "LAKI-LAKI",
        "blood_type": "-",
        "address": "NGABAN",
        "neighborhood": "005/002",
        "village": "NGABAN",
        "subdistrict": "TANGGULANGIN",
        "religion": "ISLAM",
        "marital_status": "BELUM KAWIN",
        "occupation": "PELAJAR/MAHASISWA",
        "nationality": "WNI",
        "expiry_date": "SEUMUR HIDUP",
        "attachment": "https://storage.googleapis.com/my-bucket-05062022/04062022-090807.png"
    }
}
```
<h6>Rimakatawords HTTP Response</h6>
```json
{
    "status": "success",
    "message": "Here are the data!",
    "data": {
        "mudah": [
            {
                "lema": "hela",
                "nilai": "menghela",
                "url": "NA"
            },
            {
                "lema": "gaun",
                "nilai": "baju wanita model Eropa",
                "url": "NA"
            },
            {
                "lema": "onak",
                "nilai": "rotan yang berduri",
                "url": "NA"
            },
            {
                "lema": "biji",
                "nilai": "isi buah (yang apabila ditanam dapat tumbuh)",
                "url": "NA"
            }
        ],
        "sedang": [
            {
                "lema": "serah",
                "nilai": "berserah",
                "url": "NA"
            },
            {
                "lema": "lurah",
                "nilai": "kepala pemerintahan tingkat terendah",
                "url": "NA"
            },
            {
                "lema": "daerah",
                "nilai": "bagian permukaan bumi dalam kaitannya dengan keadaan alam dsb yang khusus",
                "url": "NA"
            },
            {
                "lema": "merah",
                "nilai": "warna dasar yang serupa dengan warna darah",
                "url": "NA"
            }
        ],
        "sulit": [
            {
                "lema": "musyawarah",
                "nilai": "pembahasan bersama dengan maksud mencapai keputusan atas penyelesaian masalah",
                "url": "NA"
            },
            {
                "lema": "anugerah",
                "nilai": "pemberian atau ganjaran dari pihak atas (orang besar dsb) kepada pihak bawah (orang rendah dsb)",
                "url": "NA"
            },
            {
                "lema": "prasejarah",
                "nilai": "bagian ilmu sejarah tentang zaman ketika manusia hidup dalam kebudayaan yang belum mengenal tulisan",
                "url": "NA"
            },
            {
                "lema": "antardaerah",
                "nilai": "antara daerah yang satu dan yang lain",
                "url": "NA"
            }
        ]
    }
}
```
<h6>Achievements HTTP Response</h6>
```json
{
    "status": "success",
    "message": "User's achievements on eksplor huruf successfully updated!"
}
```
<h6>Predictions HTTP Response</h6>
<p>If the user successfully pronounce the alphabet</p>
```json
{
    "status": "success",
    "message": "We have succesfully predict your recording. User's achievements on latihan mengeja huruf successfully updated!",
    "result": 1,
    "updated": true
}
```
<p>If the user unsuccessfully pronounce the alphabet</p>
```json
{
    "status": "success",
    "message": "We have successfully predict the recording! See you're result.",
    "result": 0
}
```
<p>We also generate errors such as bad request in a constructive way so that the frontend can handle it the way it should.</p>
<h6>Error handling HTTP Response Examples</h6>
<p>Firebase Errors</p>
```json
{
    "status":"fail",
    "type":"auth/id-token-expired",
    "message":"Firebase ID token has expired. Get a fresh ID token from your client app and try again (auth/id-token-expired). See https://firebase.google.com/docs/auth/admin/verify-id-tokens for details on how to retrieve an ID token."
}
```
<p>File uploaded is not supported by the server.</p>
```json
{
    "status":"fail",
    "type":"server/file-not-supported",
    "message":"We only receive audio file type. You're file type was: video"
}
```
<p>There are many more error handling in the code. We'll leave for you readers to check it out in the codes! Thank you and have a great day.</p>
