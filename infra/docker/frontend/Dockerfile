FROM node:24

# O diretório do projeto onde o package.json fica
WORKDIR /app/apps/web

# Copia dos pacotes do angular/node nativos do web app
COPY frontend/apps/web/package.json frontend/apps/web/package-lock.json ./
RUN npm install

# Cópia residual - arquivos de código (no live-reload, serão sobresscritos via Volume)
COPY frontend/apps/web/ .

EXPOSE 4200

# Iniciando ambiente Node para Angular
CMD ["npm", "run", "start", "--", "--host", "0.0.0.0", "--poll", "2000"]
