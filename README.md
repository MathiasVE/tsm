jhipster import-jdl tsm.jdl
cd gateway
yo jhipster-primeng
cd ..
mkdir kubernetes
cd kubernetes
jhipster kubernetes
cd ..
cd gateway
mvnw package -Pprod jib:dockerBuild
cd ..
cd tsm
mvnw package -Pprod jib:dockerBuild
cd ..
cd uaa
mvnw package -Pprod jib:dockerBuild