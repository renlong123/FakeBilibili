use bilibili;

drop table if exists user_;
create table user_(

    id int(15) auto_increment,
    name varchar(255) not null,
    sex int(2),
    nikename varchar(255),
    level int(5) not null,
    description varchar(255),
    email varchar(50),
    phoneNumber int(11),
    headPic varchar(255),
    fansNumber int(20),
    followsNumber itn (20),
    createTime DATE,
    lastLoginin DATE
)
drop table if exists vedio_;
create table vedio_(
    id int(15) not null,
    name varchar(255) not null,
    description text,
    vedioAddr varchar(255),
    vedioCoverPic varchar(255),
    authorid int(15),
    publishedTime DATE ,
    playTimes int(15),
    likeNumber int(15),
    likeNumber int(15),
    categoryId int(10)
)

drop table if exists category_;
create table category_(
    id int(10) not null,
    name varchar(255) not null,
    description text,
)

drop table if exists user_to_user;
create table user_to_user(
    id int(20) not null,
    userupId int(15),
    userfollowsId int(15),
    createTime DATE
)