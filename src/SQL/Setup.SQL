DROP TABLE IF EXISTS "public"."user";
DROP TABLE IF EXISTS "public"."messages";
CREATE TABLE "public"."user" ( 
  "id" SERIAL,
  "username" VARCHAR(25) NOT NULL,
  "password" VARCHAR(25) NULL,
  "isOnline" BOOLEAN NULL DEFAULT false ,
  CONSTRAINT "user_pkey" PRIMARY KEY ("id", "username"),
  CONSTRAINT "user_username_key" UNIQUE ("username"),
  CONSTRAINT "user_id_key" UNIQUE ("id")
);
CREATE TABLE "public"."messages" ( 
  "message_id" SERIAL,
  "from_user" INTEGER NULL,
  "message" VARCHAR(250) NULL,
  CONSTRAINT "messages_pkey" PRIMARY KEY ("message_id")
);
CREATE UNIQUE INDEX "Id" 
ON "public"."user" (
  "id" ASC
);
INSERT INTO "public"."user" ("id", "username", "password", "isOnline") VALUES (4, 'GIGACHAD', 'hej', false);
INSERT INTO "public"."user" ("id", "username", "password", "isOnline") VALUES (1, 'Fibbe', '1337', false);
INSERT INTO "public"."user" ("id", "username", "password", "isOnline") VALUES (3, 'Bob', '123', false);
INSERT INTO "public"."messages" ("message_id", "from_user", "message") VALUES (1, 1, 'hej världen');