package com.onyx.flickrview;

import com.onyx.flickrview.data.FlickrApiResponse;
import com.onyx.flickrview.data.Image;
import com.onyx.flickrview.viewimages.ImagesContract;
import com.onyx.flickrview.viewimages.ImagesPresenter;
import com.onyx.flickrview.webservice.IFlickrService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ImagesPresenter.class)
public class ImagesPresenterTest {

    private final Image[] IMAGES = new Image[2];
    private final Image.ImageUrl[] IMAGE_URLS = new Image.ImageUrl[2];
    private final FlickrApiResponse response = new FlickrApiResponse();

    @Mock
    private final ImagesContract.View mImagesView;

    @Mock
    private ImagesPresenter mImagesPresenter;

    @Mock
    private final IFlickrService mWebService;


    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private final ArgumentCaptor<IFlickrService.FlickrServiceCallback<FlickrApiResponse>> mApiResponseCallbackCaptor;

    public ImagesPresenterTest() {
        mImagesView = null;
        mWebService = null;
        mApiResponseCallbackCaptor=null;
    }

    @Before
    public void setupImagesPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mImagesPresenter = new ImagesPresenter(mImagesView, mWebService);
        // given some image urls build up image sample images
        IMAGE_URLS[0]= (new Image.ImageUrl("https://farm5.staticflickr.com/4540/26747405149_69887188f3_m.jpg"));
        IMAGE_URLS[1]= (new Image.ImageUrl("https://farm5.staticflickr.com/4552/26747405829_7f5649eb7b_m.jpg"));
        IMAGES[0] = new Image("Accumulation", "Pascal_Bandelier posted a photo", IMAGE_URLS[0]);
        IMAGES[1] = new Image("2017 Secret City Half", "kenshelton400 posted a photo", IMAGE_URLS[1]);
        // build fake response data
        response.setImages(IMAGES);

    }


    @Test
    public void can_show_images() {

        mImagesPresenter.loadImages(true);
        // verify that progress is shown
        verify(mImagesView).setProgressIndicator(true);

        // Callback is captured and invoked with fake api response
        assert mApiResponseCallbackCaptor != null;
        verify(mWebService).getImages(mApiResponseCallbackCaptor.capture());
        mApiResponseCallbackCaptor.getValue().onLoaded(response);

        // Then progress indicator is hidden and images are shown in UI
        verify(mImagesView).setProgressIndicator(false);
        verify(mImagesView).showImages(response.getImages());
    }

    @Test
    public void show_empty_message_when_data_is_not_available() {

        mImagesPresenter.loadImages(true);
        // verify that progress is shown
        verify(mImagesView).setProgressIndicator(true);

        // Callback is captured and invoked with fake api response
        assert mApiResponseCallbackCaptor != null;
        verify(mWebService).getImages(mApiResponseCallbackCaptor.capture());
        mApiResponseCallbackCaptor.getValue().onLoaded(null);

        // Then progress indicator is hidden and error is shown in UI
        verify(mImagesView).setProgressIndicator(false);
        verify(mImagesView).showError("Error");
    }

}
