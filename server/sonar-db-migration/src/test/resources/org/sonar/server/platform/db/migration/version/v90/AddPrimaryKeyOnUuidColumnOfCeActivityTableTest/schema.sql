CREATE TABLE "CE_ACTIVITY"(
    "UUID" VARCHAR(40) NOT NULL,
    "TASK_TYPE" VARCHAR(15) NOT NULL,
    "MAIN_COMPONENT_UUID" VARCHAR(40),
    "COMPONENT_UUID" VARCHAR(40),
    "STATUS" VARCHAR(15) NOT NULL,
    "MAIN_IS_LAST" BOOLEAN NOT NULL,
    "MAIN_IS_LAST_KEY" VARCHAR(55) NOT NULL,
    "IS_LAST" BOOLEAN NOT NULL,
    "IS_LAST_KEY" VARCHAR(55) NOT NULL,
    "SUBMITTER_UUID" VARCHAR(255),
    "SUBMITTED_AT" BIGINT NOT NULL,
    "STARTED_AT" BIGINT,
    "EXECUTED_AT" BIGINT,
    "EXECUTION_COUNT" INTEGER NOT NULL,
    "EXECUTION_TIME_MS" BIGINT,
    "ANALYSIS_UUID" VARCHAR(50),
    "ERROR_MESSAGE" VARCHAR(1000),
    "ERROR_STACKTRACE" CLOB,
    "ERROR_TYPE" VARCHAR(20),
    "WORKER_UUID" VARCHAR(40),
    "CREATED_AT" BIGINT NOT NULL,
    "UPDATED_AT" BIGINT NOT NULL
);
CREATE INDEX "CE_ACTIVITY_COMPONENT" ON "CE_ACTIVITY"("COMPONENT_UUID");
CREATE INDEX "CE_ACTIVITY_ISLAST" ON "CE_ACTIVITY"("IS_LAST", "STATUS");
CREATE INDEX "CE_ACTIVITY_ISLAST_KEY" ON "CE_ACTIVITY"("IS_LAST_KEY");
CREATE INDEX "CE_ACTIVITY_MAIN_COMPONENT" ON "CE_ACTIVITY"("MAIN_COMPONENT_UUID");
CREATE INDEX "CE_ACTIVITY_MAIN_ISLAST" ON "CE_ACTIVITY"("MAIN_IS_LAST", "STATUS");
CREATE INDEX "CE_ACTIVITY_MAIN_ISLAST_KEY" ON "CE_ACTIVITY"("MAIN_IS_LAST_KEY");