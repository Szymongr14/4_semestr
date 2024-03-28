package org.example;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MageRepositoryTest {
    MageRepository mageRepository;

    @BeforeEach
    public void setUp(){
        mageRepository = new MageRepository();
    }

    @Test
    public void delete_ObjectDoesntExist_IllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            mageRepository.delete("TEST_MAGE_THAT_DOESNT_EXIST");
        });
    }

    @Test
    public void delete_ObjectExists_ObjectDeleted() throws IllegalAccessException {
        Mage mage = new Mage("TEST_MAGE", 10);
        mageRepository.save(mage);
        try{
            mageRepository.delete("TEST_MAGE");
        }catch (IllegalArgumentException e){
            fail("Test failed - exception has been thrown");
        }
    }

    @Test
    public void save_ObjectAlreadyExists_IllegalAccessException() throws IllegalAccessException {
        Mage mage = new Mage("TEST_MAGE", 10);
        mageRepository.save(mage);

        assertThrows(IllegalAccessException.class, () -> {
            mageRepository.save(mage);
        });
    }

    @Test
    public void save_ObjectDoesntExist_ObjectSaved() throws IllegalAccessException {
        Mage testMage = new Mage("TEST_MAGE", 10);
        mageRepository.save(testMage);
    }


    @Test
    public void find_ObjectDoesntExist_EmptyOptional(){
        assertEquals(Optional.empty(), mageRepository.find("TEST_MAGE_THAT_DOESNT_EXIST"));
    }

    @Test
    public void find_ObjectExists_OptionalWithObject() throws IllegalAccessException {
        Mage mage = new Mage("TEST_MAGE", 10);
        mageRepository.save(mage);
        assertEquals(Optional.of(mage), mageRepository.find("TEST_MAGE"));
    }
}