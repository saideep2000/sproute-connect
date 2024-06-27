This project is created from 

<!-- my-project/
├── backend/
│   ├── src/main/java/com/example/demo/
│   │   ├── DemoApplication.java
│   │   ├── controller/
│   │   │   └── MessageController.java
│   │   ├── model/
│   │   │   └── Message.java
│   │   └── service/
│   │       └── MessageService.java
│   ├── src/main/resources/application.properties
│   ├── pom.xml
├── frontend/
│   ├── src/
│   │   ├── App.tsx
│   │   ├── index.tsx
│   ├── package.json
│   ├── tsconfig.json
├── docker-compose.yml
├── kubernetes/
│   ├── backend-deployment.yaml
│   ├── backend-service.yaml
│   ├── frontend-deployment.yaml
│   ├── frontend-service.yaml
│   ├── redis-deployment.yaml
│   ├── redis-service.yaml
└── README.md -->


Steps followed :

Spring Boot project using Spring Initializr :

1. Spring Web
2. Spring Data Redis (Access + Driver)
3. Lombok

Backend :

create MVC struture.

Frontend :

npx create-react-app frontend --template typescript

npm install axios


Steps followed :

cd backend

brew install maven

mvn -version

mvn -N io.takari:maven:wrapper

./mvnw clean package

java -jar target/*.jar

mkdir Dockerfile


docker build -t messaging-backend-image .

-------------------------------------------------------

cd ../frontend

mkdir Dockerfile

docker build -t messaging-frontend-image .

-------------------------------------------------------

docker tag messaging-frontend-image:latest saideepsamineni/messaging-frontend-image:latest
docker tag messaging-backend-image:latest saideepsamineni/messaging-backend-image:latest
docker push saideepsamineni/messaging-frontend-image:latest
docker push saideepsamineni/messaging-backend-image:latest


-------------------------------------------------------

minikube start


kubectl apply -f kubernetes/redis-deployment.yaml
kubectl apply -f kubernetes/redis-service.yaml
kubectl apply -f kubernetes/backend-deployment.yaml
kubectl apply -f kubernetes/backend-service.yaml
kubectl apply -f kubernetes/frontend-deployment.yaml
kubectl apply -f kubernetes/frontend-service.yaml

kubectl get pods

kubectl get svc


To kill all docker containers : 

docker images

docker stop $(docker ps -q)

-------------------------------------------------------

minikube ip

The ip address of the minikube keeps changing, we can run a file which can get the ip of the minikube and set that to the env and in the frontend we can fetch the ip from the env.

But without these we'll use ingress which to manage access to Minikube cluster services.

Ingress Controller :

minikube addons enable ingress

create ingress.yaml

Update /etc/hosts

echo "$(minikube ip) myapp.local" | sudo tee -a /etc/hosts

kubectl apply -f ingress.yaml

kubectl get pods -n kube-system | grep ingress

kubectl get ingress

kubectl logs ingress-nginx-controller-768f948f8f-6hlx4 -n ingress-nginx

kubectl get endpoints


http://myapp.local

-------------------------------------------------------

Debugging:

kubectl get pods -n ingress-nginx

kubectl logs -n ingress-nginx -l app.kubernetes.io/name=ingress-nginx

kubectl describe ingress myapp-ingress

minikube tunnel

kubectl exec -it <backend-pod-name> -- curl -I http://frontend-service:80

kubectl exec -it <frontend-pod-name> -- curl -I http://backend-service:8080/api


kubectl exec -it frontend-deployment-66d8b5dd9c-kktpm -- curl -I http://frontend-service:80
kubectl exec -it backend-deployment-69bb58bfbf-jrcgx -- curl -I http://backend-service:8080/api

minikube delete


-------------------------------------------------------

Using test-pod to test the backend with curl :

kubectl apply -f kubernetes/test-pod.yaml

kubectl exec -it test-pod -- curl -I http://backend-service:8080/api

kubectl exec -it test-pod -- curl -I http://frontend-service:80

kubectl rollout restart deployment/backend-deployment
kubectl rollout restart deployment/frontend-deployment
kubectl rollout restart deployment/ingress-nginx-controller -n ingress-nginx


Testing backend can talk to redis cluster:

kubectl exec -it <backend-pod-name> -- redis-cli -h redis-service -p 6379

kubectl exec -it backend-deployment-6776f86659-gfrpf -- redis-cli -h redis-service -p 6379


kubectl exec -it ingress-nginx-controller-595dc9f555-jm6s8 -n ingress-nginx -- /bin/sh


-------------------------------------------------------

We resolve the issue of accessing Minikube services on macOS by mapping your domain names (e.g., myapp.local) to 127.0.0.1 in the /etc/hosts file and starting minikube tunnel. This setup allows traffic directed to 127.0.0.1 to be tunneled into Minikube, enabling your local machine to access services managed by Ingress as if they were running locally.

minikube service --url=false ingress-nginx-controller -n ingress-nginx

echo "127.0.0.1 myapp.local" | sudo tee -a /etc/hosts

sudo vim /etc/hosts

kubectl rollout restart deployment/backend-deployment
kubectl rollout restart deployment/frontend-deployment
kubectl rollout restart deployment/redis-deployment
kubectl rollout restart deployment/ingress-nginx-controller -n ingress-nginx

-------------------------------------------------------

Useful commands :

minikube start --vm-driver=docker

sudo minikube tunnel

python update_env.yaml

kubectl apply -f kubernetes/redis-deployment.yaml
kubectl apply -f kubernetes/redis-service.yaml
kubectl apply -f kubernetes/backend-deployment.yaml
kubectl apply -f kubernetes/backend-service.yaml
kubectl apply -f kubernetes/frontend-deployment.yaml
kubectl apply -f kubernetes/frontend-service.yaml

kubectl get pods

kubectl get svc

kubectl rollout restart deployment/backend-deployment
kubectl rollout restart deployment/frontend-deployment
kubectl rollout restart deployment/ingress-nginx-controller -n ingress-nginx

http://myapp.local/

kubectl apply -f ingress.yaml

kubectl rollout restart deployment/ingress-nginx-controller -n ingress-nginx


-------------------------------------------------------

https://github.com/chipmk/docker-mac-net-connect

brew install chipmk/tap/docker-mac-net-connect

sudo brew services stop chipmk/tap/docker-mac-net-connect

sudo brew services start chipmk/tap/docker-mac-net-connect


minikube start --vm-driver=docker

minikube addons enable ingress

kubectl apply -f ingress.yaml


sudo sh -c 'echo "127.0.0.1 myapp.local" >> /etc/hosts'

sudo minikube tunnel


kubectl apply -f kubernetes/backend-deployment.yaml
kubectl apply -f kubernetes/backend-service.yaml
kubectl apply -f kubernetes/frontend-deployment.yaml
kubectl apply -f kubernetes/frontend-service.yaml

kubectl get pods -n ingress-nginx
kubectl get svc -n ingress-nginx




./mvnw clean package

java -jar target/*.jar


-------------------------------------------------------


minikube start --vm-driver=docker

sudo minikube tunnel

kubectl apply -f kubernetes/backend-deployment.yaml
kubectl apply -f kubernetes/backend-service.yaml

kubectl apply -f kubernetes/frontend-deployment.yaml
kubectl apply -f kubernetes/frontend-service.yaml

minikube addons enable ingress

sudo vim /etc/hosts

192.168.49.2 sprout.connect
192.168.49.2 sprout.connect.backend

kubectl apply -f ingress.yaml

kubectl rollout restart deployment/backend-deployment
kubectl rollout restart deployment/frontend-deployment
kubectl rollout restart deployment/ingress-nginx-controller -n ingress-nginx

kubectl delete pods --all --all-namespaces

minikube stop

sudo systemctl stop docker






