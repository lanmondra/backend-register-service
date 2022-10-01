package com.register.service.entities;

import java.time.LocalDateTime;

public interface ChangeControlable {
	
	Integer getId();

    LocalDateTime getCreated();

    void setCreated(LocalDateTime lastUpdate);

    LocalDateTime getUpdated();

    void setUpdated(LocalDateTime updated);

    LocalDateTime getDeleted();

    void setDeleted(LocalDateTime deleteTime);

}

