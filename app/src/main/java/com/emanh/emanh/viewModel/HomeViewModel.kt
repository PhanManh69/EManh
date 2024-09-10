package com.emanh.emanh.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emanh.emanh.model.FriendsModel

class HomeViewModel : ViewModel() {
    private val _friendsList = MutableLiveData<List<FriendsModel>>()
    val friendsList: LiveData<List<FriendsModel>> = _friendsList

    init {
        val friends = listOf(
            FriendsModel(0, "https://fiverr-res.cloudinary.com/videos/so_0.379904,t_main1,q_auto,f_auto/yqkqrmhjwpqqgznzmpen/create-your-personalized-animated-talking-avatar-using-powerful-ai.png", "You"),
            FriendsModel(1, "https://thumbs.dreamstime.com/b/generative-ai-young-smiling-man-avatar-man-brown-beard-mustache-hair-wearing-yellow-sweater-sweatshirt-d-vector-people-279560903.jpg", "emanh"),
            FriendsModel(2, "https://play-lh.googleusercontent.com/7Ak4Ye7wNUtheIvSKnVgGL_OIZWjGPZNV6TP_3XLxHC-sDHLSE45aDg41dFNmL5COA", "zulaz"),
            FriendsModel(3, "https://photo2.tinhte.vn/data/attachment-files/2022/12/6246423_image.jpg", "luoan"),
            FriendsModel(4, "https://res.cloudinary.com/upwork-cloud/video/upload/c_scale,w_1000/v1688795185/catalog/1677553414280462336/bb6arfmnh6r2z9kcp7fx.JPEG", "zoning"),
            FriendsModel(5, "https://substackcdn.com/image/fetch/f_auto,q_auto:good,fl_progressive:steep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2F70e563a8-d943-450e-beca-f92d4697a966_1024x1024.png", "ling"),
            FriendsModel(6, "https://neuroflash.com/wp-content/uploads/2022/12/feature-image-ai-avatar-maker.png", "hpluna"),
            FriendsModel(7, "https://starryai.com/cdn-cgi/image/format=avif,quality=90/https://cdn.prod.website-files.com/61554cf069663530fc823d21/6369fed004b5b041b7ed686a_download-8-min.png", "lazy"),
            FriendsModel(8, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxrTm2suzCZFB6J0DHNK4naVhHDFgUc_EViw&s", "sadboy"),
            FriendsModel(9, "https://play-lh.googleusercontent.com/FNMbkedF8iOTgcCOAWJMMO5sboykZuUFw5uIPwy-4FSMFHCXXSJYav41jdTIiKTfdJ0", "good"),
            FriendsModel(10, "https://images.media.io/images2023/ai-portrait-generator/portrait-pic1.png", "lind"),
            FriendsModel(11, "https://preview.redd.it/transform-your-selfie-into-a-stunning-ai-avatar-with-stable-v0-2z411m9dob6a1.png?width=1024&format=png&auto=webp&s=8e97db53d8c34044ce841f0110cf996fbd28f006", "pugin"),
            FriendsModel(12, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXdT9EcQENGWg-LhKhXS7KWgw6Gnz1Gl2Uag&s", "penguin"),
            FriendsModel(13, "https://cdn.hackernoon.com/images/bfqrt3x6hAVgXkezEqVTPC5AAFA2-lbc3lp3.jpeg", "lappay"),
            FriendsModel(14, "https://img.freepik.com/premium-photo/anime-boy-man-avatar-ai-generative-art_225753-12280.jpg", "pakoo"),
            FriendsModel(15, "https://starryai.com/cdn-cgi/image/format=avif,quality=90/https://cdn.prod.website-files.com/61554cf069663530fc823d21/6369fe5c04b5b062eeed6515_download-57-min.png", "bomn"),
            FriendsModel(16, "https://thumbs.dreamstime.com/b/anime-boy-man-avatar-ai-generative-art-anime-boy-avatar-ai-generative-art-273239920.jpg", "clas"),
            FriendsModel(17, "https://i.pinimg.com/originals/f7/fe/8a/f7fe8ac33ef70c986627c2f74c524ae3.jpg", "line"),
            FriendsModel(18, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTvXF-GBWIjTZI4_BhnF_nRwzzSoFT7ankNIw&s", "newzin"),
            FriendsModel(19, "https://preview.redd.it/exploring-the-kalki-avatar-in-ai-arts-a-futuristic-fusion-v0-g00w4mzoepsb1.jpg?width=640&crop=smart&auto=webp&s=52d38c8c1f349cb14c41b99b6301c1c41a2042c9", "wes"),
            FriendsModel(20, "https://static.vecteezy.com/system/resources/previews/029/796/026/large_2x/asian-girl-anime-avatar-ai-art-photo.jpg", "post"),
            FriendsModel(21, "https://static.vecteezy.com/system/resources/previews/021/907/479/large_2x/anime-girl-avatar-ai-generated-photo.jpg", "plassy"),
            FriendsModel(22, "https://static.vecteezy.com/system/resources/thumbnails/029/796/019/small_2x/asian-girl-anime-avatar-ai-art-photo.jpg", "loiad"),
            FriendsModel(23, "https://img.freepik.com/premium-photo/cute-asian-girl-kawaii-anime-avatar-ai-generative-art_225753-12242.jpg", "haha")
        )
        _friendsList.value = friends
    }
}