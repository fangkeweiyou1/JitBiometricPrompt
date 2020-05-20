package com.zhang.jitbiometricprompt;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;

import java.util.concurrent.Executor;

import static java.lang.System.out;

/**
 * todo 一定用jdk1.8
 * todo 一定用jdk1.8
 * todo 一定用jdk1.8
 * todo 一定用jdk1.8
 * todo 一定用jdk1.8
 * todo 一定用jdk1.8
 * todo 一定用jdk1.8
 * todo 一定用jdk1.8
 * todo 一定用jdk1.8
 * todo 一定用jdk1.8
 * todo 一定用jdk1.8
 * todo 一定用jdk1.8
 */
public class MainActivity extends AppCompatActivity {

    private Button tv_main_finger;
    private Handler handler = new Handler();

    private Executor executor = new Executor() {
        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_main_finger = findViewById(R.id.tv_main_finger);
        tv_main_finger.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                finger();
            }
        });
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                out.println("---<<<>>>---App can authenticate using biometrics.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                out.println("---<<<>>>---No biometric features available on this device.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                out.println("---<<<>>>---Biometric features are currently unavailable.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                out.println("---<<<>>>---The user hasn't associated any biometric credentials");
                break;
        }

    }

    void finger() {
        BiometricPrompt.PromptInfo promptInfo =
                new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("指纹登录")
                        .setSubtitle("指纹登录小标题")
                        .setNegativeButtonText("取消")
                        //todo 以下代码段展示了如何显示不需要显式用户操作来完成身份验证流程的对话框：
//                        .setConfirmationRequired(false)
                        /*
                        如果用户无法使用生物识别凭据进行身份验证，您可以通过将 true 传递到
                         setDeviceCredentialAllowed() 方法中，允许他们利用设备 PIN 码、
                         图案或密码进行身份验证。以下代码段展示了如何提供这种回退支持：
                         //todo 以下代码会有闪退,可能没有图案验证权限有关
                         */
//                        .setDeviceCredentialAllowed(true)
                        .build();

        BiometricPrompt biometricPrompt = new BiometricPrompt(MainActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                BiometricPrompt.CryptoObject authenticatedCryptoObject =
                        result.getCryptoObject();
                Toast.makeText(getApplicationContext(),
                        "验证成功", Toast.LENGTH_SHORT)
                        .show();
                // User has verified the signature, cipher, or message
                // authentication code (MAC) associated with the crypto object,
                // so you can use it in your app's crypto-driven workflows.
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        // Displays the "log in" prompt.
        biometricPrompt.authenticate(promptInfo);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
