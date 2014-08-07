package com.softserveinc.ita.mocks;

import com.softserveinc.ita.service.ViewResolverService;
import org.springframework.stereotype.Service;

@Service
public class ViewResolverServiceMock implements ViewResolverService {

    @Override
    public String choosePageByGroupId(String groupId, long currentTime) {
        return "planned";
    }
}
