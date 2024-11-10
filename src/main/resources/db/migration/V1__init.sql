CREATE TABLE videos
(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    url varchar(255),
    views integer default(0),
    created_at DATETIME DEFAULT (CURRENT_DATE),
    PRIMARY KEY (id)
);