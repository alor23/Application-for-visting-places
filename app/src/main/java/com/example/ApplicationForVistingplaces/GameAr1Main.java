package com.example.ApplicationForVistingplaces;

import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.Button;
import android.widget.TextView;

import com.google.ar.sceneform.Camera;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.collision.Ray;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.rendering.Texture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class GameAr1Main extends AppCompatActivity {

    private Scene scene;
    private Camera camera;
    private ModelRenderable bulletRenderable;
    private boolean shouldStartTimer = true;
    private int bananasLeft = 20;
    private Point point;
    private TextView bananasLeftTxt;
    private SoundPool soundPool;
    private int sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getRealSize(point);

        setContentView(R.layout.activity_ar_game_1);

        loadSoundPool();

        bananasLeftTxt = findViewById(R.id.bananasCntTxt);
        GameAr1 arFragment =
                (GameAr1) getSupportFragmentManager().findFragmentById(R.id.arFragment);


        scene = arFragment.getArSceneView().getScene();
        camera = scene.getCamera();

        addBananasToScene();
        buildBulletModel();


        Button shoot = findViewById(R.id.shootButton);

        shoot.setOnClickListener(v -> {

            if (shouldStartTimer) {
                startTimer();
                shouldStartTimer = false;
            }

            shoot();

        });


    }

    private void loadSoundPool() {

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        sound = soundPool.load(this, R.raw.blop_sound, 1);

    }

    private void shoot() {

        Ray ray = camera.screenPointToRay(point.x / 2f, point.y / 2f);
        Node node = new Node();
        node.setRenderable(bulletRenderable);
        scene.addChild(node);

        new Thread(() -> {

            for (int i = 0; i < 200; i++) {

                int finalI = i;
                runOnUiThread(() -> {

                    Vector3 vector3 = ray.getPoint(finalI * 0.1f);
                    node.setWorldPosition(vector3);

                    Node nodeInContact = scene.overlapTest(node);

                    if (nodeInContact != null) {

                        bananasLeft--;
                        bananasLeftTxt.setText("Bananas Left: " + bananasLeft);
                        scene.removeChild(nodeInContact);

                        soundPool.play(sound, 1f, 1f, 1, 0
                                , 1f);

                    }

                });

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            runOnUiThread(() -> scene.removeChild(node));

        }).start();

    }

    private void startTimer() {

        TextView timer = findViewById(R.id.timerText);

        new Thread(() -> {

            int seconds = 0;

            while (bananasLeft > 0) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                seconds++;

                int minutesPassed = seconds / 60;
                int secondsPassed = seconds % 60;

                runOnUiThread(() -> timer.setText(minutesPassed + ":" + secondsPassed));

            }

        }).start();

    }

    private void buildBulletModel() {

        Texture
                .builder()
                .setSource(this, R.drawable.texture)
                .build()
                .thenAccept(texture -> {


                    MaterialFactory
                            .makeOpaqueWithTexture(this, texture)
                            .thenAccept(material -> {

                                bulletRenderable = ShapeFactory
                                        .makeSphere(0.01f,
                                                new Vector3(0f, 0f, 0f),
                                                material);

                            });


                });

    }

    private void addBananasToScene() {

        Texture
                .builder()
                .setSource(this, R.drawable.banana_texture)
                .build()
                .thenAccept(texture -> {

                    MaterialFactory
                            .makeOpaqueWithTexture(this, texture)
                            .thenAccept(material -> {

                                ModelRenderable
                                        .builder()
                                        .setSource(this, Uri.parse("banana.sfb"))
                                        .build()
                                        .thenAccept(renderable -> {

                                            for (int i = 0; i < 20; i++) {
                                                Node node = new Node();

                                                renderable.setMaterial(material);
                                                node.setRenderable(renderable);
                                                scene.addChild(node);


                                                Random random = new Random();
                                                int x = random.nextInt(20);
                                                int z = random.nextInt(20);
                                                int y = random.nextInt(40);

                                                z = -z;

                                                node.setWorldPosition(new Vector3(
                                                        (float) x,
                                                        y / 10f,
                                                        (float) z
                                                ));


                                            }

                                        });
                            });
                });


    }
}