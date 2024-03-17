CREATE TABLE IF NOT EXISTS CHAT_LINK
(
    chat_id         bigint,
    url_id          bigint,

    primary key (chat_id, url_id),
    foreign key (chat_id) references CHAT(chat_id) on delete cascade,
    foreign key (url_id)  references Link(url_id) on delete cascade
);
