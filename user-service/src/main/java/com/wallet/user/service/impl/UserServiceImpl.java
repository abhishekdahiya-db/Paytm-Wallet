package com.wallet.user.service.impl;

import com.wallet.kafka.DTO.UserCreatedKafkaObject;
import com.wallet.user.DAO.IUserRepo;
import com.wallet.user.DTO.UserDTO;
import com.wallet.user.entity.UserEntity;
import com.wallet.user.service.UserService;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private IUserRepo repository;
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    private static final String USER_CREATED_TOPIC = "USER";

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public long registerUser(UserDTO userRequest) {
        // convert dto to entity
        UserEntity user = mapDtoToEntity(userRequest);
        // save user entity to DB
        UserEntity savedUser = repository.save(user);
        // send user created object message to kafka for notifier welcome msg and wallet creation for async functionality.
        UserCreatedKafkaObject kafkaMessage = new UserCreatedKafkaObject(savedUser.getUserId(),savedUser.getName(),savedUser.getEmailId());
        Future<SendResult<String,Object>> future = kafkaTemplate.send(USER_CREATED_TOPIC,String.valueOf(savedUser.getUserId()),kafkaMessage);
        logger.info("user created object pushed into kafka topic {} with future {}",USER_CREATED_TOPIC,future);
        // send id of created user
        return savedUser.getUserId();
    }

    private UserEntity mapDtoToEntity(UserDTO userDTO){
        UserEntity user = new UserEntity();
        user.setName(userDTO.getName());
        user.setKycNumber(userDTO.getKycNumber());
        user.setEmailId(userDTO.getEmailId());
        return user;
    }
}
