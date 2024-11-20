CREATE TABLE videos
(
    id int AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    description text,
    video_url varchar(255),
    user_id int not null,
    created_at DATETIME DEFAULT (CURRENT_TIMESTAMP),
    PRIMARY KEY (id)
);

CREATE TABLE views
(
  ip varchar(255) not null,
  video_id int not null,
  PRIMARY KEY(ip, video_id),
  FOREIGN KEY (video_id) REFERENCES videos(id)
)