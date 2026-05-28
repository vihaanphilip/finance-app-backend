-- Manual / ad-hoc FK constraint script for the budget feature.
-- This file is NOT auto-executed by Spring or Hibernate.
-- Apply after Hibernate creates the tables on first startup:
--   - Supabase: already applied via MCP apply_migration
--   - Local Docker (port 5433): apply once via psql

ALTER TABLE budget ADD CONSTRAINT budget_user_id_fkey FOREIGN KEY (user_id) REFERENCES _user(id) ON DELETE SET NULL;
ALTER TABLE earning ADD CONSTRAINT earning_budget_id_fkey FOREIGN KEY (budget_id) REFERENCES budget(id) ON DELETE SET NULL;
ALTER TABLE expense ADD CONSTRAINT expense_budget_id_fkey FOREIGN KEY (budget_id) REFERENCES budget(id) ON DELETE SET NULL;
ALTER TABLE transfer ADD CONSTRAINT transfer_budget_id_fkey FOREIGN KEY (budget_id) REFERENCES budget(id) ON DELETE SET NULL;
