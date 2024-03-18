CREATE TABLE IF NOT EXISTS CHAT_LINK
(
    chat_id         bigint,
    url_id          bigint,

    primary key (chat_id, url_id),
    foreign key (chat_id) references CHAT(chat_id),
    foreign key (url_id)  references Link(url_id)
);

ALTER TABLE CHAT_LINK
    DROP CONSTRAINT chat_link_url_id_fkey,
    ADD CONSTRAINT chat_link_url_id_fkey FOREIGN KEY (url_id) REFERENCES Link(url_id) ON DELETE CASCADE;
