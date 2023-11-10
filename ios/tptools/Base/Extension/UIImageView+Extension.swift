//
//  UIImageView+Extension.swift
//  tptools
//
//  Created by fd on 2022/11/14.
//

import Foundation
import Kingfisher

extension UIImageView {
    func loadImage(with url: String) {
        if url.isEmpty {
            return
        }
        if url.hasPrefix("http") {
            self.kf.setImage(with: URL(string: url), options: [.loadDiskFileSynchronously,.cacheOriginalImage])
        } else {
            image = UIImage(named: url)
        }
    }
}

extension UIImage {
    func setAlpha(_ alpha: CGFloat = 0.7) -> UIImage {
        let imgView = UIImageView(image: self)
        imgView.alpha = alpha
        return imgView.getImage() ?? UIImage()
    }
}
