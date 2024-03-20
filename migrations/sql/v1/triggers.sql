CREATE OR REPLACE FUNCTION delete_unique_link() RETURNS trigger AS $$
begin
    if (SELECT COUNT(*) FROM CHAT_LINK WHERE url_id = OLD.url_id) = 0 then
        DELETE FROM link WHERE url_id = OLD.url_id;
    end if;
    return null;
end;
$$ LANGUAGE plpgsql;

CREATE TRIGGER delete_unique_link
    AFTER DELETE ON CHAT_LINK
    FOR EACH ROW EXECUTE PROCEDURE delete_unique_link();
