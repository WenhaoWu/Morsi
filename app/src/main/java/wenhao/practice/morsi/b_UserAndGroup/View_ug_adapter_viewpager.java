package wenhao.practice.morsi.b_UserAndGroup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by wenhaowu on 01/02/16.
 */
public class View_ug_adapter_viewpager extends FragmentPagerAdapter {

    private String self_uid;

    private final int PAGE_COUNT = 2;

    private String tabTitles[] = new String[]{"User","Group"};

    public View_ug_adapter_viewpager(FragmentManager fm) {
        super(fm);
    }

    public void setSelf_uid(String self_uid) {
        this.self_uid = self_uid;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return View_list_fragment.newInstance(0, self_uid);
            case 1:
                return View_list_fragment.newInstance(1, self_uid);
            default:
                return View_list_fragment.newInstance(0, self_uid);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
