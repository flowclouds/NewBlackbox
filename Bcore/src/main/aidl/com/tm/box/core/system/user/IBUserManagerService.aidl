// IBUserManagerService.aidl
package com.tm.box.core.system.user;

// Declare any non-default types here with import statements
import com.tm.box.core.system.user.BUserInfo;
import java.util.List;


interface IBUserManagerService {
    BUserInfo getUserInfo(int userId);
    boolean exists(int userId);
    BUserInfo createUser(int userId);
    List<BUserInfo> getUsers();
    void deleteUser(int userId);
}
