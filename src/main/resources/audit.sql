CREATE TABLE IF NOT EXISTS tb_audit (
    id SERIAL PRIMARY KEY,
    action VARCHAR(50),
    table_name VARCHAR(50),
    old_data JSONB,
    new_data JSONB,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION audit_trigger_function()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO tb_audit(action, table_name, new_data)
        VALUES ('INSERT', TG_TABLE_NAME, row_to_json(NEW));
        RETURN NEW;
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO tb_audit(action, table_name, old_data, new_data)
        VALUES ('UPDATE', TG_TABLE_NAME, row_to_json(OLD), row_to_json(NEW));
        RETURN NEW;
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO tb_audit(action, table_name, old_data)
        VALUES ('DELETE', TG_TABLE_NAME, row_to_json(OLD));
        RETURN OLD;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER audit_tb_product
AFTER INSERT OR UPDATE OR DELETE ON tb_product
FOR EACH ROW EXECUTE FUNCTION audit_trigger_function();

CREATE TRIGGER audit_tb_production
AFTER INSERT OR UPDATE OR DELETE ON tb_production
FOR EACH ROW EXECUTE FUNCTION audit_trigger_function();

CREATE TRIGGER audit_tb_user
AFTER INSERT OR UPDATE OR DELETE ON tb_user
FOR EACH ROW EXECUTE FUNCTION audit_trigger_function();
