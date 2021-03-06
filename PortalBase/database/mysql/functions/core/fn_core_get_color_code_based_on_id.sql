
DROP FUNCTION IF EXISTS fn_core_get_color_code_based_on_id;

DELIMITER $$

CREATE  FUNCTION fn_core_get_color_code_based_on_id(p_color_id INT(10)) RETURNS VARCHAR(60) 
    READS SQL DATA
BEGIN
	DECLARE v_color_code VARCHAR(60);

        SELECT  ccm.color_code
        INTO    v_color_code
        FROM    core_color_master ccm
        WHERE   ccm.color_id = p_color_id LIMIT 1;

	RETURN(v_color_code);
END $$

DELIMITER ;
