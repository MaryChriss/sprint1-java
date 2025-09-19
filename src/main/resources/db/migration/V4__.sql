
ALTER TABLE zona
DROP CONSTRAINT IF EXISTS "FK_ZONA_PATIO";
ALTER TABLE zona
    ADD CONSTRAINT "FK_ZONA_PATIO"
        FOREIGN KEY (patio_id) REFERENCES patio (id)
            ON DELETE CASCADE;

ALTER TABLE moto
DROP CONSTRAINT IF EXISTS "FK_MOTO_ON_ZONA";
ALTER TABLE moto
    ADD CONSTRAINT "FK_MOTO_ON_ZONA"
        FOREIGN KEY (zona_id) REFERENCES zona (id)
            ON DELETE SET NULL;

ALTER TABLE moto
DROP CONSTRAINT IF EXISTS "FK_MOTO_PATIO";
ALTER TABLE moto
    ADD CONSTRAINT "FK_MOTO_PATIO"
        FOREIGN KEY (patio_id) REFERENCES patio (id)
            ON DELETE CASCADE;

ALTER TABLE gateway
DROP CONSTRAINT IF EXISTS "FK_GATEWAY_ON_LOCALID_ZONA";
ALTER TABLE gateway
    ADD CONSTRAINT "FK_GATEWAY_ON_LOCALID_ZONA"
        FOREIGN KEY (localid_zona_id) REFERENCES zona (id)
            ON DELETE SET NULL;

-- ZONA → PATIO  (CASCADE ao apagar pátio)
ALTER TABLE zona
DROP CONSTRAINT IF EXISTS fk_zona_patio,
  DROP CONSTRAINT IF EXISTS "FK_ZONA_PATIO";
ALTER TABLE zona
    ADD CONSTRAINT fk_zona_patio
        FOREIGN KEY (patio_id) REFERENCES patio (id)
            ON DELETE CASCADE;

-- MOTO → ZONA  (ao apagar zona, a moto permanece, zona_id vira NULL)
ALTER TABLE moto
DROP CONSTRAINT IF EXISTS fk_moto_on_zona,
  DROP CONSTRAINT IF EXISTS "FK_MOTO_ON_ZONA";
ALTER TABLE moto
    ADD CONSTRAINT fk_moto_on_zona
        FOREIGN KEY (zona_id) REFERENCES zona (id)
            ON DELETE SET NULL;

-- MOTO → PATIO  (CASCADE ao apagar pátio some a moto)
ALTER TABLE moto
DROP CONSTRAINT IF EXISTS fk_moto_patio,
  DROP CONSTRAINT IF EXISTS "FK_MOTO_PATIO";
ALTER TABLE moto
    ADD CONSTRAINT fk_moto_patio
        FOREIGN KEY (patio_id) REFERENCES patio (id)
            ON DELETE CASCADE;

-- GATEWAY → ZONA  (ao apagar zona, gateway fica sem local, NULL)
ALTER TABLE gateway
DROP CONSTRAINT IF EXISTS fk_gateway_on_localid_zona,
  DROP CONSTRAINT IF EXISTS "FK_GATEWAY_ON_LOCALID_ZONA";
ALTER TABLE gateway
    ADD CONSTRAINT fk_gateway_on_localid_zona
        FOREIGN KEY (localid_zona_id) REFERENCES zona (id)
            ON DELETE SET NULL;

-- EVENTO_ALPR → MOTO  (se moto cair, manter histórico e zerar FK)
ALTER TABLE evento_alpr
DROP CONSTRAINT IF EXISTS fk_eventoalpr_on_moto,
  DROP CONSTRAINT IF EXISTS "FK_EVENTOALPR_ON_MOTO";
ALTER TABLE evento_alpr
    ADD CONSTRAINT fk_eventoalpr_on_moto
        FOREIGN KEY (moto_id) REFERENCES moto (id)
            ON DELETE SET NULL;

-- EVENTO_WIFI → MOTO  (mesma ideia: manter histórico)
ALTER TABLE evento_wifi
DROP CONSTRAINT IF EXISTS fk_eventowifi_on_moto,
  DROP CONSTRAINT IF EXISTS "FK_EVENTOWIFI_ON_MOTO";
ALTER TABLE evento_wifi
    ADD CONSTRAINT fk_eventowifi_on_moto
        FOREIGN KEY (moto_id) REFERENCES moto (id)
            ON DELETE SET NULL;

-- EVENTO_WIFI → GATEWAY  (se gateway cair pela zona, manter evento)
ALTER TABLE evento_wifi
DROP CONSTRAINT IF EXISTS fk_eventowifi_on_gateway_id_gateway,
  DROP CONSTRAINT IF EXISTS "FK_EVENTOWIFI_ON_GATEWAY_ID_GATEWAY";
ALTER TABLE evento_wifi
    ADD CONSTRAINT fk_eventowifi_on_gateway_id_gateway
        FOREIGN KEY (gateway_id_gateway) REFERENCES gateway (id_gateway)
            ON DELETE SET NULL;
