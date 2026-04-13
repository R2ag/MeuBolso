FROM node:24

WORKDIR /app

# Copia do front para que as pastas do host (apps/ e libs/) fiquem no container do dev
COPY frontend/package.json frontend/package-lock.json ./
RUN npm install

COPY frontend/ .

EXPOSE 4200

# Rodando o angular cli local do node_modules no modo start, e adicionando host 0.0.0.0 para ser acessível do hospedeiro
CMD ["npm", "run", "start", "--", "--host", "0.0.0.0"]
