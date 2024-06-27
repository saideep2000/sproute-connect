import os
import subprocess

def get_minikube_ip():
    try:
        result = subprocess.run(['minikube', 'ip'], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        result.check_returncode()
        return result.stdout.strip()
    except subprocess.CalledProcessError as e:
        print(f"Error retrieving Minikube IP: {e.stderr}")
        return None

def update_env_file(minikube_ip, backend_port, env_file_path='.env'):
    env_vars = {
        'REACT_APP_MINIKUBE_IP': minikube_ip,
        'REACT_APP_BACKEND_PORT': backend_port
    }
    
    with open(env_file_path, 'w') as env_file:
        for key, value in env_vars.items():
            env_file.write(f"{key}={value}\n")

def main():
    backend_port = '30001'
    minikube_ip = get_minikube_ip()

    if minikube_ip:
        update_env_file(minikube_ip, backend_port)
        print(f".env file updated with Minikube IP: {minikube_ip} and backend port: {backend_port}")
    else:
        print("Failed to update .env file due to error in retrieving Minikube IP.")

if __name__ == "__main__":
    main()
