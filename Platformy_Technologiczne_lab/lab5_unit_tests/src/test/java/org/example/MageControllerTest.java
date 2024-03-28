package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MageControllerTest {
    private MageRepository mageRepositoryMock;
    private MageController mageController;

    @BeforeEach
    void setUp() {
        mageRepositoryMock = Mockito.mock(MageRepository.class);
        mageController = new MageController(mageRepositoryMock);
    }

    @Test
    void find_ObjectDoesntExist_StringNotFound() {
        Mockito.when(mageRepositoryMock.find("TEST_MAGE")).thenReturn(Optional.empty());
        assertEquals("not found", mageController.find("TEST_MAGE"));
    }

    @Test
    void find_ObjectExist_ObjectToString(){
        Mage testMage = new Mage("TEST_MAGE", 10);
        Mockito.when(mageRepositoryMock.find("TEST_MAGE")).thenReturn(Optional.of(testMage));
        assertEquals(testMage.toString(), mageController.find("TEST_MAGE"));
    }

    @Test
    void delete_ObjectDoesntExist_StringBadRequest() {
        Mockito.doThrow(IllegalArgumentException.class).when(mageRepositoryMock).delete("TEST_MAGE");
        assertEquals("bad request", mageController.delete("TEST_MAGE"));
    }

    @Test
    void delete_ObjectExist_StringDone() {
        assertEquals("done", mageController.delete("TEST_MAGE"));
    }

    @Test
    void save_ObjectExist_StringBadRequest() throws IllegalAccessException {
        Mockito.doThrow(IllegalAccessException.class).when(mageRepositoryMock).save(Mockito.any());
        assertEquals("bad request", mageController.save("TEST_MAGE", "10"));
    }

    @Test
    void save_ObjectDoesntExist_StringDone(){
        assertEquals("done", mageController.save("TEST_MAGE", "10"));
    }
}