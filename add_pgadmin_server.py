import requests
import time

# Configurações do servidor
pgadmin_url = 'http://localhost:15432'  # Nome do serviço do pgAdmin
email = 'root@root.com'
password = 'root'
server_name = 'PA4'
server_data = {
    "name": server_name,
    "group_id": None,
    "host": "novo_bd",
    "port": 5432,
    "username": "postgres",
    "password": "root",
    "ssl_mode": "prefer"
}

# Função para verificar se o pgAdmin está pronto
def check_pgadmin_ready():
    try:
        response = requests.get(pgadmin_url)
        return response.status_code == 200
    except requests.exceptions.RequestException:
        return False

# Função para fazer login no pgAdmin
def pgadmin_login():
    login_url = f"{pgadmin_url}/api/auth/login"
    response = requests.post(login_url, json={"email": email, "password": password})
    if response.status_code == 200:
        return response.json()['refresh_token']
    else:
        raise Exception("Login failed")

# Função para adicionar um novo servidor
def add_server(refresh_token):
    headers = {
        'Authorization': f'Bearer {refresh_token}',
        'Content-Type': 'application/json'
    }
    servers_url = f"{pgadmin_url}/api/servers"
    response = requests.post(servers_url, json=server_data, headers=headers)
    if response.status_code == 201:
        print("Servidor adicionado com sucesso!")
    else:
        print("Erro ao adicionar servidor:", response.json())

# Main
if __name__ == "__main__":
    # Aguarde o pgAdmin iniciar
    for _ in range(120):  # Aumentado para 120 segundos
        if check_pgadmin_ready():
            try:
                token = pgadmin_login()
                add_server(token)
            except Exception as e:
                print(f"Tentativa falhou: {e}. Tentando novamente em 1 segundo...")
                time.sleep(1)
            break
        else:
            print("pgAdmin não está pronto, tentando novamente em 1 segundo...")
            time.sleep(1)
    else:
        print("Falha ao conectar ao pgAdmin após várias tentativas.")
