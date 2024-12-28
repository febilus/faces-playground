-- CREATE TABLE user (
--     id BIGINT NOT NULL AUTO_INCREMENT,
--     uemail VARCHAR(255) NOT NULL,
--     birthday DATE NOT NULL,
--     upassword VARCHAR(255) NOT NULL,
--     PRIMARY KEY (id)
-- );

INSERT INTO play_user (uemail, birthday, upassword) VALUES ('play@ground.dummy', '2000-01-01','playPassword');

-- CREATE TABLE zip_city (
--     id BIGINT NOT NULL AUTO_INCREMENT,
--     zip VARCHAR(255) NOT NULL,
--     city VARCHAR(255) NOT NULL,
--     city_normalized VARCHAR(255) NOT NULL,
--     PRIMARY KEY (id)
-- );

INSERT INTO zip_city (zip, city, city_normalized) VALUES ('35444','Biebertal, Hessen', 'biebertal'),('35418','Buseck','buseck'),('35713','Eschenburg','eschenburg'),('35463','Fernwald','fernwald');
INSERT INTO zip_city (zip, city, city_normalized) VALUES ('35390','Gießen','giessen'),('35392','Gießen','giessen'),('35394','Gießen','giessen'),('35396','Gießen, Lahn','giessen');
INSERT INTO zip_city (zip, city, city_normalized) VALUES ('35398','Gießen','giessen'),('35423','Lich, Hessen','giessen'),('35104','Lichtenfels, Hessen','lichtenfels'),('35440','Linden','linden');
INSERT INTO zip_city (zip, city, city_normalized) VALUES ('35516','Münzenberg','muenzenberg'),('35410','Hungen','hungen');

-- CREATE TABLE zip_street (
--     id BIGINT NOT NULL AUTO_INCREMENT,
--     zip VARCHAR(255) NOT NULL,
--     street VARCHAR(255) NOT NULL,
--     street_normalized VARCHAR(255) NOT NULL,
--     PRIMARY KEY (id)
-- );

INSERT INTO zip_street (zip, street, street_normalized) VALUES ('35392','Frankfurter Str.','frankfurterstrasse'),('35394','Friedensstraße','fiedensstrasse'),('35390','Am Ludwigsplatz','amludwigsplatz');
INSERT INTO zip_street (zip, street, street_normalized) VALUES ('35390','Alicenstraße','alicenstrasse'),('35390','Ludwigstraße','ludwigsstrasse'),('35392 ','Heinrich-Buff-Ring','heinrichbuffring');
INSERT INTO zip_street (zip, street, street_normalized) VALUES ('35423','Hungener Str.','hungenerstrasse'),('35423','Kolnhäuser Str.','kolnhaeuserstrasse'),('35423','Kirchhofsgasse','kirchhoffgasse');
INSERT INTO zip_street (zip, street, street_normalized) VALUES ('35418','Zeilstraße','zeilstrasse'),('35418 ','Karlsbader Str.','karlsbaderstrasse'),('35444','Am Schindwasen','shindwasen');
INSERT INTO zip_street (zip, street, street_normalized) VALUES ('35444','Am Bornberg','bornberg'),('35444','Dresdener Str.','dresdnerstrasse'),('35516','Steinbergstraße','steinbergstrasse');
INSERT INTO zip_street (zip, street, street_normalized) VALUES ('35516','Eichergasse','eichergasse'),('35410','Friedberger Str.','friedbergeerstrasse'),('35410','An den Holleräckern','andenholleraeckern');
INSERT INTO zip_street (zip, street, street_normalized) VALUES ('35410','Friedensstraße','friedensstrasse'),('35410','Mozartstraße','mozartstrasse'),('35410','Robert-Koch-Str.','robertkochstrasse');
INSERT INTO zip_street (zip, street, street_normalized) VALUES ('35440','Robert-Bosch-Str.','robertboschstrasse');