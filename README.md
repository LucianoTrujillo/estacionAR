# estacionAR
Una aplicación web en grails que revoluciona el funcionamiento de los parquímetros en Argentina

## Requisitos

### Grails 5.1.7

```
Simply open a new terminal and enter:

$ curl -s https://get.sdkman.io | bash

Follow the on-screen instructions to complete installation.

Open a new terminal or type the command:

$ source "$HOME/.sdkman/bin/sdkman-init.sh"

Then install the latest stable Grails version:

$ sdk install grails 5.1.7

If prompted, make this your default version. After installation is complete it can be tested with:

$ grails -version

That's all there is to it!
```

### node 14.20.0
install NVM:

```
curl https://raw.githubusercontent.com/creationix/nvm/master/install.sh | bash 
source ~/.profile  
```

install node:
```
nvm install 14.20.0
nvm use default 14.20.0
```

## correr Frontend (puerto 3000)

```
cd frontend
yarn
yarn start
```

## correr Backend (puerto 8080)

```
cd server
grails run-app
```

# correr Backend Tests 
```
cd server
grails test-app
```
