package edu.ncu.zww.app.wei_im.mvp.model.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import edu.ncu.zww.app.wei_im.client.ClientControl;
import edu.ncu.zww.app.wei_im.client.HttpUtil;
import edu.ncu.zww.app.wei_im.database.RealmHelper;
import edu.ncu.zww.app.wei_im.mvp.contract.OperaFGContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Invitation;
import edu.ncu.zww.app.wei_im.mvp.model.bean.StatusText;
import edu.ncu.zww.app.wei_im.mvp.model.db.InvitationDao;
import edu.ncu.zww.app.wei_im.utils.BeanTransfer;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType.FRIEND_REQUEST;
import static edu.ncu.zww.app.wei_im.mvp.model.bean.TranObjectType.GROUP_REQUEST;

public class AddContactModelImpl implements OperaFGContract.AddContactModel {

    private InvitationDao invitationDao;

    public AddContactModelImpl() {
        invitationDao = new InvitationDao();
    }

    // 是否已存在该邀请
    public boolean hasInvitation(Integer account) {
        return invitationDao.hasInvitation(account);
    }

    public Observable<List> queryContacts(final String account, final Integer type){
        return Observable.create(new ObservableOnSubscribe<List>(){
            @Override
            public void subscribe(final ObservableEmitter<List> emitter) throws Exception {
                String url = HttpUtil.getQueryContactUrl(account,type);
                HttpUtil.sendOkHttpRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.getStackTrace();
                        emitter.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        LogUtil.d("获取list线程:" + Thread.currentThread().getName());
                        System.out.println();
                        String responseText = response.body().string();
                        Gson gson = new Gson();
                        List<Contact> list = gson.fromJson(responseText,new TypeToken<List<Contact>>(){}.getType());
                        emitter.onNext(list);
                        emitter.onComplete();
                    }
                });
            }
        });
    }

    @Override
    public Observable<String> addContact(final Contact toUser, final String info, final Integer type) {
        return Observable.create(new ObservableOnSubscribe<String>(){

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                Contact fromUser = BeanTransfer.userToContact(ApplicationData.getInstance().getUserInfo());
                Invitation invitation = new Invitation();
                invitation.setUuid(UUID.randomUUID().toString());
                invitation.setToUser(toUser);
                invitation.setInfo(info);
                invitation.setCreateDate(new Date());
                invitation.setType(type);
                invitation.setStatus(StatusText.CONTACT_WAIT);

                if (type==0) {
                    String tranType = FRIEND_REQUEST;
                    ClientControl.sendConOrGroupRequest(invitation,tranType);
                    // 保存邀请数据进数据库
                    RealmHelper.getInstance().saveInvitation(invitation);
                } else {
                    String tranType = GROUP_REQUEST;
                    ClientControl.sendConOrGroupRequest(invitation,tranType);
                }
                emitter.onNext("发送成功");
                emitter.onComplete();
            }

        });
    }

    public void closeRealm() {
        invitationDao.closeRealm();
    }

}
