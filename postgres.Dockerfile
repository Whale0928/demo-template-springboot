FROM postgres:15.1

RUN apt-get update
RUN apt-get install -y \
    apt-utils \
    wget \
    zsh \
    curl \
    locales \
    tzdata
RUN sh -c "$(wget -O- https://github.com/deluan/zsh-in-docker/releases/download/v1.1.5/zsh-in-docker.sh)"

# LOCALE 설정
RUN localedef -i ko_KR -c -f UTF-8 -A /usr/share/locale/locale.alias ko_KR.UTF-8
ENV LANG ko_KR.utf8

# localtime 에 링크할 시간대를 링크.
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

# 원하는 시간대 링크후 아래 수행시, /etc/timezone 파일변경됨.
RUN dpkg-reconfigure -f noninteractive tzdata

#docker run -d  \
#    --name postgres-15 \
#    --network your-netword \
#    --restart=always \
#    -p 5432:5432 \
#    -e POSTGRES_PASSWORD=your_password \
#    -e PGDATA=/var/lib/postgresql/data/pgdata \
#    -v ~/postgres-15/data:/var/lib/postgresql/data \
#    postgres15
