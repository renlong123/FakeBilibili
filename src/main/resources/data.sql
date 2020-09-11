use bilibili;

drop table if exists user_;
create table user_(
    id int(15) auto_increment,
    name varchar(255) not null,
    password varchar(255) not null,
    sex int(2),
    nikename varchar(255),
    level int(5) not null DEFAULT 0,
    description varchar(255),
    email varchar(50),
    phoneNumber int(11),
    headPic varchar(255),
    fansNumber int(20),
    followsNumber int(20),
    createTime DATE,
    lastLoginin DATE,
    primary key(id)
)
drop table if exists vedio_;
create table vedio_(
    id int(15) auto_increment,
    name varchar(255) not null,
    description text,
    vedioAddr varchar(255),
    vedioCoverPic varchar(255),
    authorid int(15),
    publishedTime DATE ,
    playTimes int(15),
    likeNumber int(15),
    dislikeNumber int(15),
    categoryId int(10),
    primary key(id)
)

drop table if exists category_;
create table category_(
    id int(10) auto_increment,
    name varchar(255) not null,
    description text,
    primary key(id)
)

drop table if exists user_to_user;
create table user_to_user(
    id int(20) auto_increment,
    userupId int(15),
    userfollowsId int(15),
    createTime DATE,
    primary key(id)
)

drop table if exists commits_;
create table commits_(
    id int(20) auto_increment,
    createTime DATE ,
    lastModified DATE ,
    contents varchar(10000);
    authorid int(15);
    commitsId int(20),
    videoId int(15),
    primary key(id)
)