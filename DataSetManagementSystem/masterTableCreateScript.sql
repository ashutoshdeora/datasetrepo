CREATE TABLE "RSAAPP"."DATASETMASTER"
  (
    "DATASETID"    NUMBER NOT NULL ENABLE,
    "DATASETNAME"  VARCHAR2(255 CHAR) NOT NULL ENABLE,
    "CREATEDBY"    VARCHAR2(40 CHAR) NOT NULL ENABLE,
    "UPDATEDBY"    VARCHAR2(40 CHAR) NOT NULL ENABLE,
    "CREATIONDATE" TIMESTAMP NOT NULL ENABLE,
    "UPDATEDATE"   TIMESTAMP NOT NULL ENABLE,
    "STATUS"       VARCHAR2(20 CHAR) NOT NULL ENABLE,
    CONSTRAINT "DATASET_PK" PRIMARY KEY ("DATASETID")
  );
CREATE TABLE "RSAAPP"."DATASETHISTORY"
  (
    "DATASETHISTORYID" NUMBER NOT NULL ENABLE,
    "DATASETID"        NUMBER NOT NULL ENABLE,
    "DATASETNAME"      VARCHAR2(250 CHAR) NOT NULL ENABLE,
    "CREATEDBY"        VARCHAR2(40 CHAR) NOT NULL ENABLE,
    "UPDATEDBY"        VARCHAR2(40 CHAR) NOT NULL ENABLE,
    "CREATIONDATE"     TIMESTAMP NOT NULL ENABLE,
    "UPDATEDATE"       TIMESTAMP NOT NULL ENABLE,
    CONSTRAINT "DATASETHISTORY_PK" PRIMARY KEY ("DATASETHISTORYID"),
    CONSTRAINT "DATASETHISTORY_FK2" FOREIGN KEY ("DATASETID") REFERENCES "RSAAPP"."DATASETMASTER" ("DATASETID") ENABLE
  ) ;
CREATE TABLE "RSAAPP"."DATASETRUN"
  (
    "DATASETRUNID"       NUMBER NOT NULL ENABLE,
    "PARENTDATASETRUNID" NUMBER ,
    "DATASETID"          NUMBER NOT NULL ENABLE,
    "RUNBY"              VARCHAR2(40 CHAR) NOT NULL ENABLE,
    "RUNTIME"            TIMESTAMP (6) NOT NULL ENABLE,
    "RUNSTATUS"          VARCHAR2(20 CHAR) NOT NULL ENABLE,
    "RUNPHASE"           VARCHAR2(20 CHAR) NOT NULL ENABLE,
    "READYFORRUN"        VARCHAR2(20 CHAR) NOT NULL ENABLE,
    "DATASETRUNCOMMENT"  VARCHAR2(2000 CHAR),
    CONSTRAINT "DATASETRUN_PK" PRIMARY KEY ("DATASETRUNID"),
    CONSTRAINT "DATASETRUN_FK1" FOREIGN KEY ("DATASETID") REFERENCES "RSAAPP"."DATASETMASTER" ("DATASETID") ENABLE
  ) ;
-------------------------------------------------------- feature tables -----------------------------------------------
CREATE TABLE "RSAAPP"."FEATUREMASTER"
  (
    "FEATUREID"                 NUMBER NOT NULL ENABLE,
    "FEATURESET"                VARCHAR2(20 CHAR) NOT NULL ENABLE,
    "CREATEDBY"                 VARCHAR2(40 CHAR) NOT NULL ENABLE,
    "UPDATEDBY"                 VARCHAR2(40 CHAR) NOT NULL ENABLE,
    "CREATIONDATE"              TIMESTAMP NOT NULL ENABLE,
    "UPDATEDATE"                TIMESTAMP NOT NULL ENABLE,
    "STATUS"                    VARCHAR2(20 CHAR) NOT NULL ENABLE,
    "BA"                        VARCHAR2(255 CHAR) NULL,
    "FEATUREDATASETCATAGOERY"   VARCHAR2(255 CHAR) NULL,
    "FEATUREGROUPING"           VARCHAR2(255 CHAR) NULL,
    "FEATUREOWNER"              VARCHAR2(255 CHAR) NULL,
    "FEATUREROLLOUT"            VARCHAR2(2 CHAR) NULL,
    "FEATURESTATUS"             VARCHAR2(255 CHAR) NULL,
    "FEATUREPHASE"              VARCHAR2(255 CHAR) NULL,
    "FEATURETESTEXECUTIONPHASE" VARCHAR2(255 CHAR) NULL,
    "FEATURETESTSCRIPTCOMMENTS" VARCHAR2(4000 CHAR) NULL,
    "FEATURETESTSCRIPTNAME"     VARCHAR2(4000 CHAR) NULL,
    "OWNER"                     VARCHAR2(255 CHAR) NULL,
    "RECORDSTATUS"              NUMBER(10) NULL,
    CONSTRAINT "FEATUREMASTER_PK" PRIMARY KEY ("FEATUREID")
  );
CREATE TABLE "RSAAPP"."DATASETFEATUREHISTORY"
  (
    "FEATUREHISTORYID"          NUMBER NOT NULL ENABLE,
    "FEATURESET"                VARCHAR2(20 CHAR) NOT NULL ENABLE,
    "DATASETID"                 NUMBER NOT NULL ENABLE,
    "FEATUREMASTERID"           NUMBER NOT NULL ENABLE,
    "CREATEDBY"                 VARCHAR2(40 CHAR) NOT NULL ENABLE,
    "UPDATEDBY"                 VARCHAR2(40 CHAR) NOT NULL ENABLE,
    "CREATIONDATE"              TIMESTAMP NOT NULL ENABLE,
    "UPDATEDATE"                TIMESTAMP NOT NULL ENABLE,
    "BA"                        VARCHAR2(255 CHAR) NULL,
    "FEATUREDATASETCATAGOERY"   VARCHAR2(255 CHAR) NULL,
    "FEATUREGROUPING"           VARCHAR2(255 CHAR) NULL,
    "FEATUREOWNER"              VARCHAR2(255 CHAR) NULL,
    "FEATUREROLLOUT"            VARCHAR2(2 CHAR) NULL,
    "FEATURESTATUS"             VARCHAR2(255 CHAR) NULL,
    "FEATUREPHASE"              VARCHAR2(255 CHAR) NULL,
    "FEATURETESTEXECUTIONPHASE" VARCHAR2(255 CHAR) NULL,
    "FEATURETESTSCRIPTCOMMENTS" VARCHAR2(4000 CHAR) NULL,
    "FEATURETESTSCRIPTNAME"     VARCHAR2(4000 CHAR) NULL,
    "OWNER"                     VARCHAR2(255 CHAR) NULL,
    "RECORDSTATUS"              NUMBER(10) NULL,
    CONSTRAINT "FEATUREHISTORY_PK" PRIMARY KEY ("FEATUREHISTORYID") ENABLE ,
    CONSTRAINT "FEATUREHISTORY_FK1" FOREIGN KEY ("DATASETID") REFERENCES "RSAAPP"."DATASETMASTER" ("DATASETID") ENABLE ,
    CONSTRAINT "FEATUREHISTORY_FK2" FOREIGN KEY ("FEATUREMASTERID") REFERENCES "RSAAPP"."FEATUREMASTER" ("FEATUREID") ENABLE
  );
CREATE TABLE "RSAAPP"."DATASETFEATURE"
  (
    "FEATUREID" NUMBER NOT NULL ENABLE,
    "DATASETID" NUMBER NOT NULL ENABLE,
    CONSTRAINT "FEATURE_FK1" FOREIGN KEY ("DATASETID") REFERENCES "RSAAPP"."DATASETMASTER" ("DATASETID") ENABLE,
    CONSTRAINT "FEATURE_FK2" FOREIGN KEY ("FEATUREID") REFERENCES "RSAAPP"."FEATUREMASTER" ("FEATUREID") ENABLE
  ) ;
CREATE TABLE "RSAAPP"."FEATURERUN"
  (
    "FEATURERUNID"    NUMBER NOT NULL ENABLE,
    "FEATUREMASTERID" NUMBER NOT NULL ENABLE,
    "DATASETRUNID"    NUMBER NOT NULL ENABLE,
    "STATUS"          VARCHAR2(20 CHAR) NOT NULL ENABLE,
    CONSTRAINT "FEATURERUN_PK" PRIMARY KEY ("FEATURERUNID") ENABLE,
    CONSTRAINT "FEATURERUN_FK1" FOREIGN KEY ("DATASETRUNID") REFERENCES "RSAAPP"."DATASETRUN" ("DATASETRUNID") ENABLE ,
    CONSTRAINT "FEATURERUN_FK2" FOREIGN KEY ("FEATUREMASTERID") REFERENCES "RSAAPP"."FEATUREMASTER" ("FEATUREID") ENABLE
  ) ;
-------------------------------------------------------- Accounts tables -----------------------------------------------------------
CREATE TABLE "RSAAPP"."ACCOUNTMASTER"
  (
    "ACCOUNTID"    NUMBER NOT NULL ENABLE,
    "ACCOUNTNAME"  VARCHAR2(200) NOT NULL ENABLE,
    "ACCOUNTSETID" NUMBER NOT NULL ENABLE,
    "CREATEDBY"    VARCHAR2(40 CHAR) NOT NULL ENABLE,
    "UPDATEDBY"    VARCHAR2(40 CHAR) NOT NULL ENABLE,
    "CREATIONDATE" TIMESTAMP NOT NULL ENABLE,
    "UPDATEDATE"   TIMESTAMP NOT NULL ENABLE,
    "STATUS"       VARCHAR2(20 CHAR) NOT NULL ENABLE,
    CONSTRAINT "ACCOUNTMASTER_PK" PRIMARY KEY ("ACCOUNTID") ENABLE
  );
CREATE TABLE "RSAAPP"."DATASETACCOUNTHISTORY"
  (
    "ACCOUNTHISTORYID" NUMBER NOT NULL ENABLE,
    "DATASETMASTERID"  NUMBER NOT NULL ENABLE,
    "ACCOUNTMASTERID"  NUMBER NOT NULL ENABLE,
    "CREATEDBY"        VARCHAR2(40 CHAR) NOT NULL ENABLE,
    "UPDATEDBY"        VARCHAR2(40 CHAR) NOT NULL ENABLE,
    "CREATIONDATE"     TIMESTAMP NOT NULL ENABLE,
    "UPDATEDATE"       TIMESTAMP NOT NULL ENABLE,
    CONSTRAINT "ACCOUNTHISTORY_PK" PRIMARY KEY ("ACCOUNTHISTORYID") ENABLE,
    CONSTRAINT "ACCOUNTHISTORY_FK1" FOREIGN KEY ("DATASETMASTERID") REFERENCES "RSAAPP"."DATASETMASTER" ("DATASETID") ENABLE ,
    CONSTRAINT "ACCOUNTHISTORY_FK2" FOREIGN KEY ("ACCOUNTMASTERID") REFERENCES "RSAAPP"."ACCOUNTMASTER" ("ACCOUNTID") ENABLE
  ) ;
CREATE TABLE "RSAAPP"."DATASETACCOUNT"
  (
    "ACCOUNTMASTERID" NUMBER,
    "DATASETMASTERID" NUMBER NOT NULL ENABLE,
    CONSTRAINT "ACCOUNT_FK2" FOREIGN KEY ("DATASETMASTERID") REFERENCES "RSAAPP"."DATASETMASTER" ("DATASETID") ENABLE,
    CONSTRAINT "ACCOUNT_FK1" FOREIGN KEY ("ACCOUNTMASTERID") REFERENCES "RSAAPP"."ACCOUNTMASTER" ("ACCOUNTID") ENABLE
  ) ;
CREATE TABLE "RSAAPP"."ACCOUNTRUN"
  (
    "ACCOUNTRUNID"    NUMBER NOT NULL ENABLE,
    "ACCOUNTMASTERID" NUMBER NOT NULL ENABLE,
    "DATASETRUNID"    NUMBER NOT NULL ENABLE,
    CONSTRAINT "ACCOUNTRUN_PK" PRIMARY KEY ("ACCOUNTRUNID") ENABLE,
    CONSTRAINT "ACCOUNTRUN_FK1" FOREIGN KEY ("DATASETRUNID") REFERENCES "RSAAPP"."DATASETRUN" ("DATASETRUNID") ENABLE,
    CONSTRAINT "ACCOUNTRUN_FK2" FOREIGN KEY ("ACCOUNTMASTERID") REFERENCES "RSAAPP"."ACCOUNTMASTER" ("ACCOUNTID") ENABLE
  );
---------------------------------------------------------------------- DEFECTS ---------------------------------------------------------------
CREATE TABLE "RSAAPP"."DATASETRUNDEFECT"
  (
    "RUNDEFECTID"        NUMBER NOT NULL ENABLE,
    "DATASETRUNID"       NUMBER NOT NULL ENABLE,
    "FEATURERUNID"       NUMBER ,
    "HPQCDEFECTID"       NUMBER NOT NULL ENABLE,
    "DEFECTSTATUS"       VARCHAR2(80 CHAR) NOT NULL ENABLE,
    "DEFECTSEVRITY"      VARCHAR2(80 CHAR) NOT NULL ENABLE,
    "DEFECTOWNER"        VARCHAR2(80 CHAR) ,
    "DEFECTCAUSE"        VARCHAR2(80 CHAR) ,
    "DEFECTNAME"         VARCHAR2(400 CHAR) ,
    "DEFECTPRIORITY"     VARCHAR2(80 CHAR) ,
    "DEFECTCREATIONDATE" VARCHAR2(80 CHAR) ,
    "DEFECTLASTMODIFIES" VARCHAR2(80 CHAR) ,
    "DEFECTCLOSINGDATE"  VARCHAR2(80 CHAR) ,
    "DEFECTRAISEDBY"     VARCHAR2(80 CHAR) ,
    CONSTRAINT "DATASETRUNDEFECT_PK" PRIMARY KEY ("RUNDEFECTID" ,"DATASETRUNID") ENABLE,
    CONSTRAINT "DATASETRUNDEFECT_FK1" FOREIGN KEY ("FEATURERUNID") REFERENCES "RSAAPP"."FEATURERUN" ("FEATURERUNID") ,
    CONSTRAINT "DATASETRUNDEFECT_FK2" FOREIGN KEY ("DATASETRUNID") REFERENCES "RSAAPP"."DATASETRUN" ("DATASETRUNID") ENABLE
  ) ;
CREATE TABLE "RSAAPP"."USERDETAILS"
  (
    "USER_ID"      NUMBER(10) NOT NULL,
    "ACCESSLEVEL"  VARCHAR2(255 CHAR) NOT NULL ENABLE,
    "EMPID"        VARCHAR2(255 CHAR) NOT NULL ENABLE,
    "PASSWORD"     VARCHAR2(255 CHAR) NOT NULL ENABLE,
    "USERNAME"     VARCHAR2(255 CHAR) NOT NULL ENABLE,
    "HPQCUSERID"   VARCHAR2(255 CHAR) NOT NULL ENABLE,
    "HPQCPASSWORD" VARCHAR2(255 CHAR) NOT NULL ENABLE,
    "CREATEDBY"    VARCHAR2(40 CHAR) NOT NULL ENABLE,
    "UPDATEDBY"    VARCHAR2(40 CHAR) NOT NULL ENABLE,
    "CREATIONDATE" TIMESTAMP NOT NULL ENABLE,
    "UPDATEDATE"   TIMESTAMP NOT NULL ENABLE,
    CONSTRAINT "USERDETAILS_PK" PRIMARY KEY ("USER_ID") ENABLE
  );
CREATE TABLE "RSAAPP"."MASTERDATA"
  (
    "ID"           NUMBER(10) NOT NULL,
    "CATAGORY"     VARCHAR2(255 CHAR),
    "CATDATA"      VARCHAR2(255 CHAR),
    "CREATEDBY"    VARCHAR2(40 CHAR) NOT NULL ENABLE,
    "UPDATEDBY"    VARCHAR2(40 CHAR) NOT NULL ENABLE,
    "CREATIONDATE" TIMESTAMP NOT NULL ENABLE,
    "UPDATEDATE"   TIMESTAMP NOT NULL ENABLE,
    CONSTRAINT "MASTERDATA_PK" PRIMARY KEY ("ID") ENABLE
  );
CREATE TABLE "RSAAPP"."SEQUENCE"
  (
    "SEQ_NAME"  VARCHAR2(50 CHAR) NOT NULL ENABLE,
    "SEQ_COUNT" NUMBER(38,0),
    PRIMARY KEY ("SEQ_NAME") ENABLE
  ) ;