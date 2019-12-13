package com.moaplanet.gosingadmin.main.submenu.charge.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.main.submenu.charge.activity.CardRegisterActivity;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.req.ReqRegisterCardDto;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResRegisterCardDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import rx.android.schedulers.AndroidSchedulers;

public class CardInfoRegisterFragment extends BaseFragment {

    private ProgressBar mLoading;

    private EditText cardNumberOne, cardNumberTwo, cardNumberThree, cardNumberFour, etYear, etMonth,
            birthNumber, pw, cardName;

    @Override
    public int layoutRes() {
        return R.layout.fragment_card_info_register;
    }

    @Override
    public void initView(View view) {
        mLoading = view.findViewById(R.id.pr_fragment_card_info_register_loading);
        mLoading.setVisibility(View.GONE);

        cardNumberOne =
                view.findViewById(R.id.et_fragment_card_info_register_card_number_1);

        cardNumberTwo =
                view.findViewById(R.id.et_fragment_card_info_register_card_number_2);

        cardNumberThree =
                view.findViewById(R.id.et_fragment_card_info_register_card_number_3);

        cardNumberFour =
                view.findViewById(R.id.et_fragment_card_info_register_card_number_4);

        etMonth = view.findViewById(R.id.et_fragment_card_info_register_card_validity_1);

        etYear = view.findViewById(R.id.et_fragment_card_info_register_card_validity_2);

        birthNumber = view.findViewById(R.id.et_fragment_card_info_register_card_birth);

        pw = view.findViewById(R.id.et_fragment_card_info_register_card_password);

        cardName = view.findViewById(R.id.et_fragment_card_info_register_title_card_nick_name);
    }

    @Override
    public void initListener() {

        CommonTitleBar commonTitle = view.findViewById(R.id.title_fragment_card_info_register);
        RxView.clicks(commonTitle.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                });

        Button button = view.findViewById(R.id.btn_fragment_card_info_register);
        RxView.clicks(button)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> checkCardInfoField());

        cardNumberOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 4) {
                    cardNumberTwo.requestFocus();
                }
            }
        });

        cardNumberTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 4) {
                    cardNumberThree.requestFocus();
                }
            }
        });

        cardNumberThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 4) {
                    cardNumberFour.requestFocus();
                }
            }
        });

        cardNumberFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 4) {
                    etMonth.requestFocus();
                }
            }
        });

        etYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 2) {
                    birthNumber.requestFocus();
                }
            }
        });

        etMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 2) {
                    etYear.requestFocus();
                }
            }
        });

        birthNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 10) {
                    pw.requestFocus();
                }
            }
        });

        pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 2) {
                    cardName.requestFocus();
                }
            }
        });
    }

    /**
     * 카드 등록에 필요한 데이터들 체크
     */
    private void checkCardInfoField() {
        ReqRegisterCardDto reqRegisterCardDto = new ReqRegisterCardDto();
        // 카드 번호 체크
        // 카드 번호 입력화면1
        if (inputCardNumberCheck(cardNumberOne)) {
            reqRegisterCardDto.setCardNumberOne(cardNumberOne.getText().toString());
        } else {
            Toast.makeText(view.getContext(),
                    "카드 번호를 입력해주세요.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // 카드 번호 입력화면2
        if (inputCardNumberCheck(cardNumberTwo)) {
            reqRegisterCardDto.setCardNumberTwo(cardNumberTwo.getText().toString());
        } else {
            Toast.makeText(view.getContext(),
                    "카드 번호를 입력해주세요.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // 카드 번호 입력화면3
        if (inputCardNumberCheck(cardNumberThree)) {
            reqRegisterCardDto.setCardNumberThree(cardNumberThree.getText().toString());
        } else {
            Toast.makeText(view.getContext(),
                    "카드 번호를 입력해주세요.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // 카드 번호 입력화면4
        if (inputCardNumberCheck(cardNumberFour)) {
            reqRegisterCardDto.setCardNumberFour(cardNumberFour.getText().toString());
        } else {
            Toast.makeText(view.getContext(),
                    "카드 번호를 입력해주세요.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // 카드 유효기간 체크
        // 년도
        if (etYear.getText().length() == 2) {
            reqRegisterCardDto.setYear(etYear.getText().toString());
        } else {
            Toast.makeText(view.getContext(),
                    "카드 유효기간을 입력해주세요.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // 월
        if (etMonth.getText().length() == 2) {
            reqRegisterCardDto.setMonth(etMonth.getText().toString());
        } else {
            Toast.makeText(view.getContext(),
                    "카드 유효기간을 입력해주세요.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // 생년월일 혹은 사업자등록번호 체크
        if (birthNumber.getText().length() == 6 || birthNumber.getText().length() == 10) {
            reqRegisterCardDto.setBirth(birthNumber.getText().toString());
        } else {
            Toast.makeText(view.getContext(),
                    "생년월일 또는 사업자등록번호를 입력해주세요.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // 패스워드
        if (pw.getText().length() == 2) {
            reqRegisterCardDto.setPw(pw.getText().toString());
        } else {
            Toast.makeText(view.getContext(),
                    "패스워드를 입력해 주세요.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // 카드 닉네임
        reqRegisterCardDto.setCardName(cardName.getText().toString());

        onRegisterCard(reqRegisterCardDto);
    }

    /**
     * 카드 등록 서버 통신
     *
     * @param reqRegisterCardDto req Dto
     */
    private void onRegisterCard(ReqRegisterCardDto reqRegisterCardDto) {
        onStartLoading(mLoading);
        RetrofitService.getInstance()
                .getGoSingApiService()
                .onServerRegisterCard(
                        reqRegisterCardDto.getCardNumberOne(),
                        reqRegisterCardDto.getCardNumberTwo(),
                        reqRegisterCardDto.getCardNumberThree(),
                        reqRegisterCardDto.getCardNumberFour(),
                        reqRegisterCardDto.getYear(),
                        reqRegisterCardDto.getMonth(),
                        reqRegisterCardDto.getPw(),
                        reqRegisterCardDto.getBirth(),
                        reqRegisterCardDto.getCardName()
                ).enqueue(new MoaAuthCallback<ResRegisterCardDto>(
                RetrofitService.getInstance().getMoaAuthConfig(),
                RetrofitService.getInstance().getSessionChecker()
        ) {
            @Override
            public void onFinalResponse(Call<ResRegisterCardDto> call, ResRegisterCardDto resModel) {

                if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                    if (getActivity() != null) {
                        getActivity().setResult(GoSingConstants.ACTION_RESULT_CODE_REGISTER_CARD);
                        getActivity().finish();
                    }
                } else if (resModel.getDetailCode() == 201) {
                    Toast.makeText(view.getContext(),
                            "이미 등록된 카드입니다.",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(view.getContext(),
                            "다시 시도해 주세요",
                            Toast.LENGTH_SHORT)
                            .show();
                }
                onStopLoading(mLoading);

            }

            @Override
            public void onFinalFailure(Call<ResRegisterCardDto> call, boolean isSession, Throwable t) {
                onStopLoading(mLoading);
                Toast.makeText(view.getContext(),
                        "다시 시도해 주세요",
                        Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onFinalNotSession() {
                super.onFinalNotSession();
                onStopLoading(mLoading);
                onNotSession();
            }
        });

    }

    @Override
    protected void onStartLoading(View viewLoading) {
        super.onStartLoading(viewLoading);
        if (getActivity() != null && getActivity() instanceof CardRegisterActivity) {
            CardRegisterActivity cardRegisterActivity = (CardRegisterActivity) getActivity();
            cardRegisterActivity.setLoading(true);
        }
    }

    @Override
    protected void onStopLoading(View viewLoading) {
        super.onStopLoading(viewLoading);
        if (getActivity() != null && getActivity() instanceof CardRegisterActivity) {
            CardRegisterActivity cardRegisterActivity = (CardRegisterActivity) getActivity();
            cardRegisterActivity.setLoading(false);
        }
    }

    /**
     * 카드 번호 입력화면의 데이터가 정상적으로 들어가졌는지 체크
     *
     * @param editText 카드번호 입력 화면 뷰
     */
    private boolean inputCardNumberCheck(EditText editText) {
        return editText.getText().length() == 4;
    }
}
