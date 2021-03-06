# This is a file for automation startup scripts in VM
# sudo apt install git
# git clone https://github.com/rmscoal/ABA.git
cd
sudo apt-get update && sudo apt update
sudo apt install g++ gcc make
sudo apt install python3
sudo apt install python3-pip 
sudo pip install numpy
sudo pip install librosa
sudo pip install tensorflowjs
sudo pip install pandas
sudo apt install ffmpeg

curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.1/install.sh | bash
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"
[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"
nvm install v16.15.0

npm install -g npm@8.12.1
npm install -g pm2
cd ABA/backend && npm install

cd
sudo apt-get install libcap2-bin
sudo setcap cap_net_bind_service=+ep `readlink -f \`which node\``