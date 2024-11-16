-- Criar tabela de auditoria
CREATE TABLE IF NOT EXISTS tb_audit (
    id BIGSERIAL PRIMARY KEY,
    entity_name VARCHAR(50),
    action VARCHAR(10),
    details TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Função para a auditoria de produtos
CREATE OR REPLACE FUNCTION audit_product()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO tb_audit (entity_name, action, details)
        VALUES ('Product', 'inserido', CONCAT('ID: ', NEW.id, ', Nome: ', NEW.name, ', Descrição: ', NEW.description));
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO tb_audit (entity_name, action, details)
        VALUES ('Product', 'atualizado', CONCAT('ID: ', NEW.id, ', Nome: ', NEW.name));
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO tb_audit (entity_name, action, details)
        VALUES ('Product', 'excluído', CONCAT('ID: ', OLD.id, ', Nome: ', OLD.name));
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- Criar Trigger para tb_product
CREATE TRIGGER audit_product_trigger
AFTER INSERT OR UPDATE OR DELETE ON tb_product
FOR EACH ROW EXECUTE FUNCTION audit_product();

-- Função para a auditoria de produção
CREATE OR REPLACE FUNCTION audit_production()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO tb_audit (entity_name, action, details)
        VALUES ('Production', 'inserido', CONCAT('ID: ', NEW.idProduction, ', Quantidade: ', NEW.planQuantity));
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO tb_audit (entity_name, action, details)
        VALUES ('Production', 'atualizado', CONCAT('ID: ', NEW.idProduction, ', Quantidade: ', NEW.planQuantity));
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO tb_audit (entity_name, action, details)
        VALUES ('Production', 'excluído', CONCAT('ID: ', OLD.idProduction, ', Quantidade: ', OLD.planQuantity));
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- Criar Trigger para tb_production
CREATE TRIGGER audit_production_trigger
AFTER INSERT OR UPDATE OR DELETE ON tb_production
FOR EACH ROW EXECUTE FUNCTION audit_production();

-- Função para a auditoria de usuários
CREATE OR REPLACE FUNCTION audit_user()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO tb_audit (entity_name, action, details)
        VALUES ('User', 'inserido', CONCAT('ID: ', NEW.id, ', Username: ', NEW.username));
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO tb_audit (entity_name, action, details)
        VALUES ('User', 'atualizado', CONCAT('ID: ', NEW.id, ', Username: ', NEW.username));
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO tb_audit (entity_name, action, details)
        VALUES ('User', 'excluído', CONCAT('ID: ', OLD.id, ', Username: ', OLD.username));
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- Criar Trigger para tb_user
CREATE TRIGGER audit_user_trigger
AFTER INSERT OR UPDATE OR DELETE ON tb_user
FOR EACH ROW EXECUTE FUNCTION audit_user();
