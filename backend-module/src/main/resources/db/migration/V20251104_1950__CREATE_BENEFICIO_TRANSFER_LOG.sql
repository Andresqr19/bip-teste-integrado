CREATE TABLE beneficio_transfer_log (
    id BIGSERIAL PRIMARY KEY,

    from_id BIGINT,
    to_id BIGINT,

    amount NUMERIC(15,2) NOT NULL,

    from_valor_before NUMERIC(15,2),
    from_valor_after NUMERIC(15,2),

    to_valor_before NUMERIC(15,2),
    to_valor_after NUMERIC(15,2),

    success BOOLEAN NOT NULL,
    message VARCHAR(255),

    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_beneficio_transfer_log_from_id_created_at ON beneficio_transfer_log (from_id, created_at);
CREATE INDEX idx_beneficio_transfer_log_to_id_created_at ON beneficio_transfer_log (to_id, created_at);