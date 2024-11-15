CREATE TABLE videos
(
    id int AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    description varchar(255),
    video_url varchar(255),
    views integer default(0),
    user_id int not null,
    created_at DATETIME DEFAULT (CURRENT_DATE),
    PRIMARY KEY (id)
);