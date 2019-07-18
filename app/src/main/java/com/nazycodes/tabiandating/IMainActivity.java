package com.nazycodes.tabiandating;

import com.nazycodes.tabiandating.models.Message;
import com.nazycodes.tabiandating.models.User;

public interface IMainActivity {
    void inflateViewProfileFragment(User user);
    void onMessageSelected(Message message);
    void onBackPressed();
}
