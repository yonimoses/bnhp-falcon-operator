mvn clean package -DskipTests

docker rmi $(docker images |grep 'test')

docker rmi -f test:$1
docker build -t yonimoses/test:$1 .
docker login
docker push yonimoses/test:$1
