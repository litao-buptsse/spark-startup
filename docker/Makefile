IMAGE_MAIN_NAME='clouddev/spark-startup'
IMAGE_VERSION='1.0'
IMAGE=$(IMAGE_MAIN_NAME):$(IMAGE_VERSION)

REGISTRY='registry.docker.dev.sogou-inc.com:5000'

ifdef NO_CACHE
    BUILD_PARAM='--no-cache=true'
else
    BUILD_PARAM=
endif

all: build

clean:
	build/sbt clean

dist:
	cd ..; build/sbt assembly; cd -
	
build: dist
	mkdir -p .tmp
	mv ../target/scala-2.10/*-assembly-*.jar .tmp
	cp ../bin/run.sh .tmp
	cp ../conf/* .tmp
	docker build $(BUILD_PARAM) -t $(IMAGE_MAIN_NAME) .
	rm -fr .tmp

push: build
	docker tag -f $(IMAGE_MAIN_NAME) $(REGISTRY)/$(IMAGE) && docker push $(REGISTRY)/$(IMAGE)
