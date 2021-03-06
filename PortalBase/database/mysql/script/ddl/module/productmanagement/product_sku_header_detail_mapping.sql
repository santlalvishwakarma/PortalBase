CREATE TABLE product_sku_header_detail_mapping (
  product_sku_header_detail_mapping_id int(10) unsigned NOT NULL AUTO_INCREMENT,
  product_id int(10) unsigned DEFAULT NULL,
  product_sku_id int(10) unsigned DEFAULT NULL,
  default_sku TINYINT(1),
  table_name varchar(1000) DEFAULT NULL,
  details_id int(10) DEFAULT NULL,
  created_by varchar(50) DEFAULT NULL,
  created_date datetime NOT NULL,
  modified_by varchar(50) DEFAULT NULL,
  modified_date datetime NOT NULL,
  PRIMARY KEY (product_sku_header_detail_mapping_id),
  KEY FK_product_sku_header_detail_mapping_1 (product_id),
  KEY FK_product_sku_header_detail_mapping_2 (product_sku_id),
  KEY Index_4 (details_id),
  CONSTRAINT FK_product_sku_header_detail_mapping_1 FOREIGN KEY (product_id) REFERENCES product_header (product_id),
  CONSTRAINT FK_product_sku_header_detail_mapping_2 FOREIGN KEY (product_sku_id) REFERENCES product_sku_header (product_sku_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;