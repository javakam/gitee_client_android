package com.gitee.android.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.ando.library.base.BaseMvvmFragment
import com.ando.library.utils.GlideOptionsProvider
import com.ando.library.utils.glide.intoCustomTarget
import com.bumptech.glide.Glide
import com.gitee.android.R
import com.gitee.android.common.CacheManager
import com.gitee.android.databinding.FragmentMineBinding
import com.gitee.android.utils.BlurBitmapUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.E

/**
 * Title: MineFragment
 * <p>
 * Description: 我的
 * </p>
 * @author ChangBao
 * @date 2020/10/16  15:20
 */
@AndroidEntryPoint
class MineFragment : BaseMvvmFragment<FragmentMineBinding>() {

    override val layoutId: Int = R.layout.fragment_mine

    override fun initView(savedInstanceState: Bundle?) {
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CacheManager.getUserInfo()?.apply {
            binding.avatar = avatar_url
            binding.tvUsername.text = name
            binding.tvEmail.text = "邮箱: $email"
            binding.tvBlog.text = "博客: $blog"
            binding.tvCreatedAt.text = "创建于: $created_at"

            Glide.with(this@MineFragment)
                .load(avatar_url)
                .apply(GlideOptionsProvider.noAnimate(R.mipmap.ic_head_default))
                .intoCustomTarget({ resource, _ ->
                    val blurBitmap =
                        BlurBitmapUtil.blurBitmap(activity, resource.toBitmap(), 0.4F)
                    binding.ivTop.setImageBitmap(blurBitmap)
                }, {
                    // handle error drawable
                })
        }
    }

}