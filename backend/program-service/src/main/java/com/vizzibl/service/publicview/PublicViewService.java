package com.vizzibl.service.publicview;

import com.vizzibl.dto.UserBadgeViewUrlDTO;
import com.vizzibl.response.ResponseObject;

public interface PublicViewService {

    ResponseObject getUserBadgeView(String publicViewId);

    ResponseObject getUserBadgeViewUrl(String tenantId, String headerUserId, UserBadgeViewUrlDTO userBadgeViewUrlDTO);

    void addUserPublicView(String tenantId, UserBadgeViewUrlDTO userBadgeViewUrlDTO);

}
