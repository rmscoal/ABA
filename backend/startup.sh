sudo apt-get update && sudo apt update
sudo apt install python3
sudo apt install python3-pip 
sudo apt install git
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.1/install.sh | bash
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"
[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"

nvm install v16.15.0
git clone https://github.com/rmscoal/ABA.git
sudo pip install librosa
sudo pip install numpy
sudo apt install g++ gcc make
sudo pip install tensorflowjs
cd ABA/backend && npm install
cd && npm install -g pm2