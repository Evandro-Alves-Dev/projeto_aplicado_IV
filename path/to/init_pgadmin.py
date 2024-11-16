import subprocess
import sys
import time

# Função para instalar o módulo requests
def install(package):
    subprocess.check_call([sys.executable, "-m", "pip", "install", package])

# Tenta instalar requests
try:
    import requests
except ImportError:
    print("Módulo 'requests' não encontrado. Instalando...")
    install('requests')

# Configurações do pgAdmin
pgadmin_url = "http://db-admin"  # Nome do serviço do pgAdmin
email = "root@root.com"
password = "root"

# Espera o pgAdmin estar pronto
print("Esperando o pgAdmin iniciar...")
max_attempts = 30  # Número máximo de tentativas
attempt = 0
while attempt < max_attempts:
    try:
        response = requests.get(pgadmin_url)
        response.raise_for_status()
        print("pgAdmin acessível!")
        break
    except requests.exceptions.RequestException:
        print("pgAdmin ainda não está acessível. Tentando novamente...")
        time.sleep(5)  # Espera 5 segundos antes de tentar novamente
        attempt += 1

if attempt == max_attempts:
    print("O pgAdmin não ficou acessível a tempo.")
    exit(1)

# Faz login no pgAdmin
session = requests.Session()
login_data = {
    'email': email,
    'password': password
}

# Tentativas de login
attempt = 0
while attempt < max_attempts:
    login_response = session.post(f"{pgadmin_url}/login", data=login_data)

    if login_response.status_code == 200:
        print("Login bem-sucedido!")
        break
    else:
        print("Tentativa de login falhou. Tentando novamente...")
        time.sleep(5)  # Espera 5 segundos antes de tentar novamente
        attempt += 1

if attempt == max_attempts:
    print("Não foi possível fazer login no pgAdmin.")
    exit(1)

# Dados do servidor que você deseja adicionar
server_data = {
    "host": "projeto_aplicado",  # Nome do serviço do PostgreSQL
    "port": 5432,
    "name": "PostgreSQL",
    "username": "postgres",
    "password": "root",
    "ssl_mode": "prefer"
}

# Adiciona o servidor
add_server_url = f"{pgadmin_url}/server"  # URL para adicionar servidor
add_server_response = session.post(add_server_url, json=server_data)

if add_server_response.status_code in (200, 201):
    print("Servidor adicionado com sucesso!")
else:
    print("Erro ao adicionar o servidor:", add_server_response.text)
