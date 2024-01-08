pushd Simple\ DTU\ Pay\ Server
./mvnw package
# Create a new docker image if necessary.
docker-compose build
# Restarts the container with the new image if necessary
docker-compose up -d
# The server stays running.
# To terminate the server run docker-compose down in the
# code-with-quarkus direcgtory
# clean up images
docker image prune -f
popd

# Give the Web server a chance to finish start up
sleep 2

pushd Simple\ DTU\ Pay\ Client
mvn test
popd
