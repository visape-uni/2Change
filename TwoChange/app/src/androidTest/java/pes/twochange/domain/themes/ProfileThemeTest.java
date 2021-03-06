package pes.twochange.domain.themes;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import pes.twochange.domain.Utils;
import pes.twochange.domain.callback.ProfileResponse;
import pes.twochange.domain.model.ModelAdapter;
import pes.twochange.domain.model.Profile;
import pes.twochange.services.Firebase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProfileThemeTest {

    private static final String LOG_TAG = "PROFILE_THEME_TEST_LOG";
    private CountDownLatch lock = new CountDownLatch(1);
    private Profile receivedProfile;
    private Profile testingProfile;

    private String lw() {
        return Utils.generateRandomWord(10);
    }

    private String sw() {
        return Utils.generateRandomWord(5);
    }

    private void insertTestingProfile() {
        Firebase.getInstance().insert(
                "profile",
                testingProfile.getUsername(),
                new ModelAdapter<Profile>() {
                    @Override
                    public Class classType() {
                        return Profile.class;
                    }

                    @Override
                    public Profile object() {
                        return testingProfile;
                    }
                }
        );
    }

    private void deleteTestingProfile() {
        Firebase.getInstance().delete("profile", testingProfile.getUsername());
    }

    @Test
    public void getCompleteProfile() throws InterruptedException {

        receivedProfile = null;

        testingProfile = new Profile(
                lw(),
                lw(),
                sw(),
                sw(),
                lw(),
                new Profile.Address(
                        sw(),
                        sw(),
                        sw(),
                        sw(),
                        sw())
        );

        insertTestingProfile();

        String username = testingProfile.getUsername();

        ProfileTheme.getInstance().get(
                username,
                new ProfileResponse() {
                    @Override
                    public void success(Profile profile) {
                        receivedProfile = profile;
                    }

                    @Override
                    public void failure(String s) {
                    }
                }
        );

        lock.await(5, TimeUnit.SECONDS);

        assertNotNull("Timeout (5s) or no profile with username = " + username, receivedProfile);
        assertEquals("Those profiles should be equal", testingProfile.toString(), receivedProfile.toString());

        deleteTestingProfile();
    }

    @Test
    public void getIncompleteProfile() throws InterruptedException {

        receivedProfile = null;

        testingProfile = new Profile(lw(), lw(), sw(), null, null, null);

        insertTestingProfile();

        String username = testingProfile.getUsername();

        ProfileTheme.getInstance().get(
                username,
                new ProfileResponse() {
                    @Override
                    public void success(Profile profile) {
                        receivedProfile = profile;
                    }

                    @Override
                    public void failure(String s) {
                    }
                }
        );

        lock.await(5, TimeUnit.SECONDS);

        assertNotNull("Timeout (5s) or no profile with username = " + username, receivedProfile);
        assertEquals("Those profiles should be equal", testingProfile.toString(), receivedProfile.toString());

        deleteTestingProfile();
    }

    @Test
    public void getNotExistingProfile() throws InterruptedException {

        receivedProfile = null;

        String username = lw();
        final String[] message = new String[1];

        ProfileTheme.getInstance().get(
                username,
                new ProfileResponse() {
                    @Override
                    public void success(Profile profile) {
                        receivedProfile = null;
                    }

                    @Override
                    public void failure(String s) {
                        receivedProfile = new Profile();
                        message[0] = s;
                    }
                }
        );

        lock.await(5, TimeUnit.SECONDS);

        assertNotNull("Timeout (5s) or existing profile with username = " + username, receivedProfile);
        assertEquals("There is no profile with this UID", message[0], "No profile with username = " + username);
    }

    @Test
    public void updateNewProfile() throws InterruptedException {

        receivedProfile = null;
        final String randomUsername = lw();

        ProfileTheme.getInstance().get(
                randomUsername,
                new ProfileResponse() {
                    @Override
                    public void success(Profile profile) {
                        receivedProfile = profile;
                    }

                    @Override
                    public void failure(String s) {

                        // Tiene que fallar para asegurarnos que el perfil no existe
                        // Creamos el nuevo perfil
                        final Profile newProfile = new Profile(randomUsername, null, null, null, null, null);

                        ProfileTheme.getInstance(newProfile).updateProfile(
                                new ProfileResponse() {
                                    @Override
                                    public void success(Profile profile) {
                                        ProfileTheme.getInstance().get(
                                                newProfile.getUsername(),
                                                new ProfileResponse() {
                                                    @Override
                                                    public void success(Profile profile) {
                                                        // Este es el nuevo perfil insertado
                                                        receivedProfile = profile;
                                                    }

                                                    @Override
                                                    public void failure(String s) {

                                                    }
                                                }
                                        );
                                    }

                                    @Override
                                    public void failure(String s) {

                                    }
                                }
                        );

                    }
                }
        );

        lock.await(5, TimeUnit.SECONDS);
        assertNotNull("Timeout (5s) or new  profile was not created", receivedProfile);
        assertEquals("New profile have been inserted", randomUsername, receivedProfile.getUsername());

        testingProfile = receivedProfile;
        deleteTestingProfile();
    }

    @Test
    public void updateExistingProfile() throws InterruptedException {

        receivedProfile = null;

        testingProfile = new Profile(
                lw(),
                lw(),
                sw(),
                null,
                null,
                new Profile.Address(sw(), null, null, null, null)
        );

        insertTestingProfile();

        final String username = testingProfile.getUsername();
        final String name = sw();
        final String zipCode = sw();

        ProfileTheme.getInstance().get(
                username,
                new ProfileResponse() {
                    @Override
                    public void success(Profile profile) {

                        profile.setName(name);
                        profile.getAddress().setZipCode(zipCode);

                        ProfileTheme.getInstance(profile).updateProfile(
                                new ProfileResponse() {
                                    @Override
                                    public void success(Profile profile) {
                                        ProfileTheme.getInstance().get(
                                                username,
                                                new ProfileResponse() {
                                                    @Override
                                                    public void success(Profile profile) {
                                                        receivedProfile = profile;
                                                    }

                                                    @Override
                                                    public void failure(String s) {

                                                    }
                                                }
                                        );
                                    }

                                    @Override
                                    public void failure(String s) {

                                    }
                                }
                        );
                    }

                    @Override
                    public void failure(String s) {

                    }
                }
        );

        lock.await(5, TimeUnit.SECONDS);
        assertNotNull("Timeout (5s) or new profile was not updated", receivedProfile);
        assertEquals("Name updated", name, receivedProfile.getName());
        assertEquals("Zip Code updated", zipCode, receivedProfile.getAddress().getZipCode());

        deleteTestingProfile();
    }

/*
    @Test
    public void updateImage() throws InterruptedException {
        final byte[][] receivedBitmap = {null};
        Bitmap image = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] arrayBitmap = baos.toByteArray();

        new ProfileTheme().updateImage("image_test_id", image, new ImageResponse() {
            @Override
            public void success(Bitmap bitmap) {
                new ProfileTheme().getImage(
                        "image_test_id",
                        new ImageResponse() {
                            @Override
                            public void success(Bitmap bitmap) {
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                receivedBitmap[0] = baos.toByteArray();
                            }

                            @Override
                            public void failure(String s) {
                            }
                        }

                );
            }

            @Override
            public void failure(String s) {

            }
        });

        lock.await(10, TimeUnit.SECONDS);
        assertNotNull("Timeout (10s) or cannot upload the new image", receivedBitmap[0]);
        assertEquals("Same image", arrayBitmap, receivedBitmap[0]);
    }
*/

}