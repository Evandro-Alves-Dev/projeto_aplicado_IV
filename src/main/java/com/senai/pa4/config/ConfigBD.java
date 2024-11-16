package com.senai.pa4.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ConfigBD {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        // SQL para criar a tabela de auditoria, se não existir
        String createAuditTableSql = """
            CREATE TABLE IF NOT EXISTS tb_audit (
                id BIGSERIAL PRIMARY KEY,
                entity_name VARCHAR(50),
                action VARCHAR(10),
                details TEXT,
                timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
        """;

        // Executar o SQL para criar a tabela de auditoria
        jdbcTemplate.execute(createAuditTableSql);
        System.out.println("Tabela de auditoria tb_audit criada ou já existe.");

        // Verificar se a trigger audit_product_trigger já existe
        String checkProductTriggerSql = """
            SELECT EXISTS (
                SELECT 1 FROM pg_trigger WHERE tgname = 'audit_product_trigger'
            );
        """;

        Boolean productTriggerExists = jdbcTemplate.queryForObject(checkProductTriggerSql, Boolean.class);
        if (!productTriggerExists) {
            createProductTrigger();
        }

        // Verificar se a trigger audit_production_trigger já existe
        String checkProductionTriggerSql = """
            SELECT EXISTS (
                SELECT 1 FROM pg_trigger WHERE tgname = 'audit_production_trigger'
            );
        """;

        Boolean productionTriggerExists = jdbcTemplate.queryForObject(checkProductionTriggerSql, Boolean.class);
        if (!productionTriggerExists) {
            createProductionTrigger();
        }

        // Verificar se a trigger audit_user_trigger já existe
        String checkUserTriggerSql = """
            SELECT EXISTS (
                SELECT 1 FROM pg_trigger WHERE tgname = 'audit_user_trigger'
            );
        """;

        Boolean userTriggerExists = jdbcTemplate.queryForObject(checkUserTriggerSql, Boolean.class);
        if (!userTriggerExists) {
            createUserTrigger();
        }
    }

    private void createProductTrigger() {
        String createProductTriggerSql = """
            CREATE OR REPLACE FUNCTION audit_product()
            RETURNS TRIGGER AS $$ 
            BEGIN 
                IF TG_OP = 'INSERT' THEN 
                    INSERT INTO tb_audit (entity_name, action, details) 
                    VALUES ('Product', 'inserido', CONCAT('ID: ', NEW.id, ', Nome: ', NEW.name, ', Descrição: ', NEW.description)); 
                ELSIF TG_OP = 'UPDATE' THEN 
                    INSERT INTO tb_audit (entity_name, action, details) 
                    VALUES ('Product', 'atualizado', CONCAT('ID: ', NEW.id, ', Nome: ', NEW.name, ', Descrição: ', NEW.description)); 
                ELSIF TG_OP = 'DELETE' THEN 
                    INSERT INTO tb_audit (entity_name, action, details) 
                    VALUES ('Product', 'excluído', CONCAT('ID: ', OLD.id, ', Nome: ', OLD.name, ', Descrição: ', OLD.description)); 
                END IF; 
                RETURN NULL; 
            END; 
            $$ LANGUAGE plpgsql;

            CREATE TRIGGER audit_product_trigger
            AFTER INSERT OR UPDATE OR DELETE ON tb_product
            FOR EACH ROW EXECUTE FUNCTION audit_product();
        """;

        jdbcTemplate.execute(createProductTriggerSql);
        System.out.println("Trigger audit_product_trigger criada.");
    }

    private void createProductionTrigger() {
        String createProductionTriggerSql = """
        CREATE OR REPLACE FUNCTION audit_production()
        RETURNS TRIGGER AS $$ 
        BEGIN 
            IF TG_OP = 'INSERT' THEN 
                INSERT INTO tb_audit (entity_name, action, details) 
                VALUES ('Production', 'inserido', CONCAT(
                    'ID: ', NEW.id_production, 
                    ', Quantidade Planejada: ', NEW.plan_quantity, 
                    ', Quantidade Real: ', NEW.real_quantity, 
                    ', Unidade: ', NEW.unit, 
                    ', Hora Início: ', NEW.start_time, 
                    ', Hora Fim: ', NEW.finish_time, 
                    ', Downtime Início: ', NEW.start_downtime, 
                    ', Downtime Fim: ', NEW.finish_downtime, 
                    ', Downtime: ', NEW.downtime, 
                    ', Tipo de Embalagem: ', NEW.package_type, 
                    ', Tipo de Rótulo: ', NEW.label_type, 
                    ', Equipamento: ', NEW.equipment, 
                    ', Turno: ', NEW.work_shift, 
                    ', Lote de Produção: ', NEW.production_batch, 
                    ', Validade: ', NEW.best_before, 
                    ', Observações: ', NEW.notes)); 
            ELSIF TG_OP = 'UPDATE' THEN 
                INSERT INTO tb_audit (entity_name, action, details) 
                VALUES ('Production', 'atualizado', CONCAT(
                    'ID: ', NEW.id_production, 
                    ', Quantidade Planejada: ', NEW.plan_quantity, 
                    ', Quantidade Real: ', NEW.real_quantity, 
                    ', Unidade: ', NEW.unit, 
                    ', Hora Início: ', NEW.start_time, 
                    ', Hora Fim: ', NEW.finish_time, 
                    ', Downtime Início: ', NEW.start_downtime, 
                    ', Downtime Fim: ', NEW.finish_downtime, 
                    ', Downtime: ', NEW.downtime, 
                    ', Tipo de Embalagem: ', NEW.package_type, 
                    ', Tipo de Rótulo: ', NEW.label_type, 
                    ', Equipamento: ', NEW.equipment, 
                    ', Turno: ', NEW.work_shift, 
                    ', Lote de Produção: ', NEW.production_batch, 
                    ', Validade: ', NEW.best_before, 
                    ', Observações: ', NEW.notes)); 
            ELSIF TG_OP = 'DELETE' THEN 
                INSERT INTO tb_audit (entity_name, action, details) 
                VALUES ('Production', 'excluído', CONCAT(
                    'ID: ', OLD.id_production, 
                    ', Quantidade Planejada: ', OLD.plan_quantity, 
                    ', Quantidade Real: ', OLD.real_quantity, 
                    ', Unidade: ', OLD.unit, 
                    ', Hora Início: ', OLD.start_time, 
                    ', Hora Fim: ', OLD.finish_time, 
                    ', Downtime Início: ', OLD.start_downtime, 
                    ', Downtime Fim: ', OLD.finish_downtime, 
                    ', Downtime: ', OLD.downtime, 
                    ', Tipo de Embalagem: ', OLD.package_type, 
                    ', Tipo de Rótulo: ', OLD.label_type, 
                    ', Equipamento: ', OLD.equipment, 
                    ', Turno: ', OLD.work_shift, 
                    ', Lote de Produção: ', OLD.production_batch, 
                    ', Validade: ', OLD.best_before, 
                    ', Observações: ', OLD.notes)); 
            END IF; 
            RETURN NULL; 
        END; 
        $$ LANGUAGE plpgsql;

        CREATE TRIGGER audit_production_trigger
        AFTER INSERT OR UPDATE OR DELETE ON tb_production
        FOR EACH ROW EXECUTE FUNCTION audit_production();
    """;

        jdbcTemplate.execute(createProductionTriggerSql);
        System.out.println("Trigger audit_production_trigger criada.");
    }


    private void createUserTrigger() {
        String createUserTriggerSql = """
            CREATE OR REPLACE FUNCTION audit_user()
            RETURNS TRIGGER AS $$ 
            BEGIN 
                IF TG_OP = 'INSERT' THEN 
                    INSERT INTO tb_audit (entity_name, action, details) 
                    VALUES ('User', 'inserido', CONCAT('ID: ', NEW.id, ', Username: ', NEW.username, ', Cargo: ', NEW.position, ', Tipo de Papel: ', NEW.role_type)); 
                ELSIF TG_OP = 'UPDATE' THEN 
                    INSERT INTO tb_audit (entity_name, action, details) 
                    VALUES ('User', 'atualizado', CONCAT('ID: ', NEW.id, ', Username: ', NEW.username, ', Cargo: ', NEW.position, ', Tipo de Papel: ', NEW.role_type)); 
                ELSIF TG_OP = 'DELETE' THEN 
                    INSERT INTO tb_audit (entity_name, action, details) 
                    VALUES ('User', 'excluído', CONCAT('ID: ', OLD.id, ', Username: ', OLD.username, ', Cargo: ', OLD.position, ', Tipo de Papel: ', OLD.role_type)); 
                END IF; 
                RETURN NULL; 
            END; 
            $$ LANGUAGE plpgsql;

            CREATE TRIGGER audit_user_trigger
            AFTER INSERT OR UPDATE OR DELETE ON tb_user
            FOR EACH ROW EXECUTE FUNCTION audit_user();
        """;

        jdbcTemplate.execute(createUserTriggerSql);
        System.out.println("Trigger audit_user_trigger criada.");
    }
}
